package io.github.darkgr.gui;

import imgui.ImGui;

public class MainMenuBar extends MolecularGUI {
    public static final int BAR_HEIGHT = 25;

    @Override
    public void process() {
        ImGui.beginMainMenuBar();

        ImGui.button("Start");
        ImGui.button("Simulation");
        ImGui.button("About");

        ImGui.endMainMenuBar();
    }
}
