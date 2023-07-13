package net.asinusgrandus.minecraft.elytrahud.flight_instruments;

import net.asinusgrandus.minecraft.elytrahud.Constants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.Window;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class FlightInstrumentData {
    public final double collisionDamageHorizontal, aspect, fov_deg, fov, hor_fov, hor_fov_deg, camera_pitch_deg;
    public final float center_height, playerFallDisctance, compass_width, heading_fives, flight_vector_size, elytra_roll, flight_vector_x, flight_vector_y, flight_vector_radius, fontHeight, heading, pixels_per_deg, horizon_width, horizon_vertical_blip_length;
    public final int air_speed, radar_height, safePlayerFallDistance, screenCenterX, screenCenterY, screenWidth, screenHeight;
    public final Vec3d player_pos, player_velocity_vector;
    public FlightInstrumentData(MinecraftClient client){
        ClientPlayerEntity player = client.player;
        TextRenderer textRenderer = client.textRenderer;
        Window window = client.getWindow();
        Camera camera = client.gameRenderer.getCamera();

        this.playerFallDisctance = player.fallDistance;
        this.safePlayerFallDistance = player.getSafeFallDistance();
        this.fontHeight = textRenderer.fontHeight;
        this.heading = camera.getYaw();
        this.heading_fives = Math.round((heading * 2f) / 10f) / 2f;

        this.aspect = (double) window.getWidth() / (double) window.getHeight();

        this.fov_deg = client.options.getFov().getValue();
        this.fov = Math.toRadians(fov_deg);
        this.hor_fov = 2.0 * Math.atan(Math.tan(fov / 2.0) * aspect);
        this.hor_fov_deg = Math.toDegrees(hor_fov);

        this.screenWidth = window.getScaledWidth();
        this.screenHeight = window.getScaledHeight();
        int screenLesser = Math.min(screenWidth, screenHeight);
        this.screenCenterX = screenWidth / 2;
        this.screenCenterY = screenHeight / 2;

        this.camera_pitch_deg = camera.getPitch();
        double camera_pitch = Math.toRadians(camera_pitch_deg);

        this.pixels_per_deg = (float) (screenHeight / fov_deg);
        float pixels_per_hor_deg = (float) (screenHeight / hor_fov_deg);

        double screen_lower_pitch_deg = camera_pitch_deg + fov_deg / 2.0D;
        double screen_upper_pitch_deg = camera_pitch_deg - fov_deg / 2.0D;

        this.horizon_width = screenWidth / 8f;
        this.horizon_vertical_blip_length = screenHeight / 160f;
        this.center_height = screenCenterY + ((float) (pixels_per_deg * -camera_pitch_deg));

        this.player_pos = player.getPos();

        Vec3d velocity = player.getVelocity().rotateY((float) Math.toRadians(camera.getYaw())).rotateX((float) Math.toRadians(camera.getPitch()));
        double velocityX = -velocity.getX();
        double velocityY = velocity.getY();
        double velocityZ = velocity.getZ();

        this.player_velocity_vector = new Vec3d(player.getVelocity().x, player.getVelocity().y - Constants.GRAVITY, player.getVelocity().z);
        this.air_speed = Math.round((float) player_velocity_vector.length() * 100f);

        double upwardSpeedAngle = Math.toDegrees(Math.atan2(velocityY, velocityZ));
        double rightSpeedAngle = Math.toDegrees(Math.atan2(velocityX, velocityZ));

        this.flight_vector_x = screenCenterX + (float) (pixels_per_hor_deg * rightSpeedAngle);
        this.flight_vector_y = screenCenterY - (float) (pixels_per_deg * upwardSpeedAngle);

        float elytra_roll_deg = velocityZ < 0.0001f ? 0f : (float) (Math.min(2, Math.max(-2, velocityX / velocityZ))) * -45f;
        this.elytra_roll = (float) Math.toRadians(elytra_roll_deg);

        this.flight_vector_size = screenLesser / 70f;
        this.flight_vector_radius = flight_vector_size / 2f;

        this.compass_width = 0.85f * horizon_width;

        this.radar_height = getRadarHeight(player, client.world);
        this.collisionDamageHorizontal = collisionDamageHorizontal(player);
    }

    /**
     * Calculate how many air blocks are below the player
     * (= How high the player is really flying in relation to the ground)
     * */
    private int getRadarHeight(ClientPlayerEntity player, ClientWorld world){
        int radar_height = 0;
        BlockPos player_blockpos = player.getBlockPos();

        if (world != null) {
            for (int y = player.getBlockPos().getY() - 1; y >= world.getBottomY() && world.isAir(new BlockPos(player_blockpos.getX(), y, player_blockpos.getZ())); y--) {
                ++radar_height;
            }
        }

        return radar_height;
    }

    private static double collisionDamageHorizontal(PlayerEntity player) {
        Vec3d velocity = player.getVelocity();
        Vec3d multiplied_velocity = velocity.multiply(0.99f, 0.98f, 0.99f);
        return multiplied_velocity.horizontalLength() * 10.0 - player.getSafeFallDistance();
    }
}
