package com.clubby.randomteleport;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.modules.entity.component.Invulnerable;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class WildCommand extends AbstractPlayerCommand {

    private static final int MIN_RADIUS = 500;
    private static final int MAX_RADIUS = 5000;

    private static final int TOP_Y = 255;

    private static final long INVULNERABLE_MS = 6_000;

    public WildCommand() {
        super("wild", "Teleport to a random location in the world.");
    }

    @Override
    protected void execute(@Nonnull CommandContext context,
                           @Nonnull Store<EntityStore> store,
                           @Nonnull Ref<EntityStore> ref,
                           @Nonnull PlayerRef playerRef,
                           @Nonnull World world) {

        TransformComponent transform = store.getComponent(ref, TransformComponent.getComponentType());
        if (transform == null) {
            context.sendMessage(Message.raw("Could not read your position"));
            return;
        }

        Vector3d origin = transform.getPosition();
        Vector3f rotation = transform.getRotation();

        double angle = ThreadLocalRandom.current().nextDouble(0, Math.PI * 2);
        int radius = ThreadLocalRandom.current().nextInt(MIN_RADIUS, MAX_RADIUS + 1);

        int x = (int) Math.floor(origin.x + Math.cos(angle) * radius);
        int z = (int) Math.floor(origin.z + Math.sin(angle) * radius);

        Vector3d targetPos = new Vector3d(
                x + 0.5,
                TOP_Y - 1,
                z + 0.5
        );

        store.addComponent(ref, Invulnerable.getComponentType(), Invulnerable.INSTANCE);
        store.addComponent(ref, Teleport.getComponentType(), new Teleport(targetPos, rotation));
        CompletableFuture.delayedExecutor(INVULNERABLE_MS, TimeUnit.MILLISECONDS).execute(() -> {
            world.execute(() -> {
                store.removeComponent(ref, Invulnerable.getComponentType());
            });
        });

        context.sendMessage(Message.raw("[WILD] Teleported to the wild: (" + (int) x + ", " + (int) z));
    }
}
