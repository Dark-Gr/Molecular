package io.github.darkgr.gui;

import imgui.ImGui;
import imgui.ImVec2;
import io.github.darkgr.Main;
import io.github.darkgr.world.Particle;
import org.joml.Vector2d;
import org.joml.Vector2f;

public class MainMenuBar extends MolecularGUI {
    public static final int BAR_HEIGHT = 25;
    private static final ImVec2 MENU_BUTTON_SIZE = new ImVec2(100, BAR_HEIGHT);

    private Vector2f popupPos = new Vector2f();

    @Override
    public void process() {
        ImGui.beginMainMenuBar();

        if(ImGui.button("Menu", MENU_BUTTON_SIZE))
            popupPos = openPopup("Menu");

        if(ImGui.button("Simulation", MENU_BUTTON_SIZE))
            popupPos = openPopup("Simulation");

        if(ImGui.button("About", MENU_BUTTON_SIZE))
            popupPos = openPopup("About");



        ImGui.setNextWindowPos(popupPos.x, popupPos.y);
        if(ImGui.beginPopup("Menu")) {
            if(ImGui.button("Exit", MENU_BUTTON_SIZE))
                System.exit(0);

            ImGui.endPopup();
        }

        ImGui.setNextWindowPos(popupPos.x, popupPos.y);
        if(ImGui.beginPopup("Simulation")) {
            if(ImGui.button("Add Particle", MENU_BUTTON_SIZE)) {
                Particle particle = new Particle(new Vector2d(750, 500), 1);
                particle.getVelocity().x = Math.random() * 2;
                particle.getVelocity().y = Math.random() * 2;
                Main.particleHolder.addParticle(particle);
                Main.particleHolder.selectParticle(particle);
            }

            if(ImGui.button(Main.INSTANCE.isPaused() ? "Resume" : "Pause", MENU_BUTTON_SIZE))
                Main.INSTANCE.togglePause();

            ImGui.endPopup();
        }

        ImGui.setNextWindowPos(popupPos.x, popupPos.y);
        if(ImGui.beginPopup("About")) {
            if(ImGui.button("Github", MENU_BUTTON_SIZE)) {
                // TODO: Open Github repository
            }

            ImGui.endPopup();
        }

        ImGui.endMainMenuBar();
    }

    private Vector2f openPopup(String id) {
        float x = ImGui.getCursorPosX();
        float y = ImGui.getCursorPosY();

        ImGui.openPopup(id);

        return new Vector2f(x - MENU_BUTTON_SIZE.x - 8, y + (int) (MENU_BUTTON_SIZE.y));
    }
}
