package io.github.darkgr.gui;

import com.badlogic.gdx.graphics.Color;
import imgui.ImGui;
import imgui.type.ImFloat;
import io.github.darkgr.Main;
import io.github.darkgr.world.Particle;
import org.joml.Vector2f;

public class InspectorGUI extends MolecularGUI {
    @Override
    public void process() {
        Particle selectedParticle = Main.particleHolder.getSelected();

        ImGui.begin("Inspector");

        ImFloat xInput = new ImFloat(selectedParticle.getPosition().x);
        ImFloat yInput = new ImFloat(selectedParticle.getPosition().y);

        ImFloat xVelocityInput = new ImFloat(selectedParticle.getVelocity().x);
        ImFloat yVelocityInput = new ImFloat(selectedParticle.getVelocity().y);

        ImFloat massInput = new ImFloat(selectedParticle.getMass());

        int[] radiusInput = new int[] { selectedParticle.getRadius() };

        float[] colorInput = new float[] {
            selectedParticle.getColor().r, selectedParticle.getColor().g,
            selectedParticle.getColor().b, selectedParticle.getColor().a
        };

        ImGui.setCursorPos(20, 40);
        ImGui.text("Position");
        ImGui.sameLine();
        ImGui.setCursorPosX(85);
        ImGui.setNextItemWidth(135);
        ImGui.inputFloat("##X", xInput);
        ImGui.sameLine();
        ImGui.setNextItemWidth(135);
        ImGui.inputFloat("##Y", yInput);

        ImGui.setCursorPosX(20);
        ImGui.setCursorPosY(ImGui.getCursorPosY() + 10);
        ImGui.text("Velocity");
        ImGui.sameLine();
        ImGui.setCursorPosX(85);
        ImGui.setNextItemWidth(135);
        ImGui.inputFloat("##VelX", xVelocityInput);
        ImGui.sameLine();
        ImGui.setNextItemWidth(135);
        ImGui.inputFloat("##VelY", yVelocityInput);

        ImGui.setCursorPosX(20);
        ImGui.setCursorPosY(ImGui.getCursorPosY() + 10);
        ImGui.text("Mass");
        ImGui.sameLine();
        ImGui.setCursorPosX(85);
        ImGui.setNextItemWidth(280);
        ImGui.inputFloat("##mass", massInput);

        ImGui.setCursorPosX(20);
        ImGui.setCursorPosY(ImGui.getCursorPosY() + 10);
        ImGui.text("Color");
        ImGui.sameLine();
        ImGui.setCursorPosX(85);

        if(ImGui.colorEdit4("##color", colorInput))
            selectedParticle.setColor(new Color(colorInput[0], colorInput[1], colorInput[2], colorInput[3]));

        ImGui.setCursorPosX(20);
        ImGui.setCursorPosY(ImGui.getCursorPosY() + 10);
        ImGui.text("Radius");
        ImGui.sameLine();
        ImGui.setCursorPosX(85);
        ImGui.setNextItemWidth(280);
        ImGui.sliderInt("##radius", radiusInput, 1, 100);

        selectedParticle.setPosition(new Vector2f(xInput.get(), yInput.get()));
        selectedParticle.setVelocity(new Vector2f(xVelocityInput.get(), yVelocityInput.get()));
        selectedParticle.setMass(massInput.get());
        selectedParticle.setRadius(radiusInput[0]);

        ImGui.end();
    }
}
