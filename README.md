# 🧭 Hytale Random Teleport

A simple Hytale plugin that adds a `/wild` command to teleport players to a random location in the world.

## Command
`/wild`

## What It Does
- Teleports the player to a random location
- Uses the player’s current position as the origin
- Random distance between 500 and 5000 blocks
- Preserves the player’s rotation
- Spawns the player above the world and lets them fall naturally
- Applies temporary fall damage protection to prevent unsafe landings

## Why This Approach
Direct terrain placement can be unreliable due to chunk generation timing and block detection, sometimes spawning players inside blocks underground. Spawning above the world avoids these issues and ensures safe, consistent teleports.

## Notes
- No cooldowns yet
- No permissions yet

## Future Plans
More updates and safety improvements coming soon.
