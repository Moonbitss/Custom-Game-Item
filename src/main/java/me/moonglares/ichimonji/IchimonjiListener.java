package me.moonglares.ichimonji;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.entity.Item;

public class IchimonjiListener implements Listener {
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private IchimonjiPlugin mainPlugin; // Replace with your actual main plugin class

    private static final long COOLDOWN = 15000L; // 15 seconds cooldown
    private static final int EFFECT_DURATION = 600; // 30 seconds duration in ticks
    private final IchimonjiPlugin plugin; // Replace YourPluginClass with your actual plugin class name

    public IchimonjiListener(IchimonjiPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Player)) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();

        if (item != null && item.getType() == Material.DIAMOND_SWORD) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasDisplayName() && ChatColor.stripColor(meta.getDisplayName()).equals("Ichimonji")) {
                UUID playerId = player.getUniqueId();
                Long cooldownExpiration = cooldowns.get(playerId);

                if (cooldownExpiration != null && cooldownExpiration > System.currentTimeMillis()) {
                    return;
                }

                cooldowns.put(playerId, System.currentTimeMillis() + COOLDOWN);

                Player target = (Player) event.getRightClicked();
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, EFFECT_DURATION, 0));
                target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, EFFECT_DURATION, 0));
                createInkEffect(target);

                target.sendMessage(ChatColor.RED + player.getName() + ChatColor.AQUA + " has splashed Ichimonji's ink on you. Your power has been reduced in half.");
                player.sendMessage(ChatColor.AQUA + "You have used Ichimonji on " + ChatColor.RED + target.getName() + ChatColor.AQUA + ".");

            }
        }
    }

    private void createInkEffect(final Player target) {
        final World world = target.getWorld();
        final Location center = target.getLocation();

        // Sound to play
        Sound inkSplashSound = Sound.SLIME_WALK; // or Sound.ENTITY_SLIME_SQUISH_SMALL for a less intense sound

        // Loop to create and drop multiple ink sacs around the player
        for (int i = 0; i < 5; i++) {
            // Generate random offsets for each ink sac
            double offsetX = (Math.random() * 2 - 1) * 2; // Random number between -2 and 2
            double offsetY = (Math.random() * 1.5); // Random number between 0 and 1.5
            double offsetZ = (Math.random() * 2 - 1) * 2; // Random number between -2 and 2

            // Create a new location based on these offsets
            Location dropLocation = center.clone().add(offsetX, offsetY, offsetZ);

            // Create ink sac item
            ItemStack inkSac = new ItemStack(Material.INK_SACK, 1);
            final Item droppedInk = world.dropItem(dropLocation, inkSac);
            droppedInk.setPickupDelay(Integer.MAX_VALUE); // Prevent the ink sacs from being picked up
            droppedInk.setCustomName("Ink Splash"); // Set a custom name for identification (optional)

            // Play slime sound at the location of each dropped ink sac
            world.playSound(dropLocation, inkSplashSound, 1.0f, 1.0f); // volume and pitch can be adjusted as desired

            // Schedule the ink sacs to be removed after a delay
            new BukkitRunnable() {
                @Override
                public void run() {
                    droppedInk.remove();
                }
            }.runTaskLater(plugin, 100); // Remove after 5 seconds (100 ticks)
        }
    }




}