package io.github.darkgr.gui;

import com.badlogic.gdx.graphics.Color;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.type.ImBoolean;
import imgui.type.ImDouble;
import io.github.darkgr.Main;
import io.github.darkgr.world.Particle;

public class InspectorGUI extends MolecularGUI {
    @Override
    public void process() {
        Particle selectedParticle = Main.particleHolder.getSelected();

        ImGui.begin("Inspector");

        if(selectedParticle == null) {
            ImGui.end();
            return;
        }

        ImDouble xInput = new ImDouble(selectedParticle.getPosition().x);
        ImDouble yInput = new ImDouble(selectedParticle.getPosition().y);

        ImBoolean movableInput = new ImBoolean(selectedParticle.isMovable());

        ImDouble xVelocityInput = new ImDouble(selectedParticle.getVelocity().x);
        ImDouble yVelocityInput = new ImDouble(selectedParticle.getVelocity().y);

        ImDouble massInput = new ImDouble(selectedParticle.getMass());

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

        if(ImGui.inputDouble("##X", xInput))
            selectedParticle.getPosition().x = xInput.get();

        ImGui.sameLine();
        ImGui.setNextItemWidth(135);

        if(ImGui.inputDouble("##Y", yInput))
            selectedParticle.getPosition().y = yInput.get();


        ImGui.setCursorPosX(20);
        ImGui.setCursorPosY(ImGui.getCursorPosY() + 10);
        ImGui.text("Velocity");
        ImGui.sameLine();
        ImGui.setCursorPosX(85);
        ImGui.setNextItemWidth(135);

        if(ImGui.inputDouble("##VelX", xVelocityInput))
            selectedParticle.getVelocity().x = xVelocityInput.get();

        ImGui.sameLine();
        ImGui.setNextItemWidth(135);

        if(ImGui.inputDouble("##VelY", yVelocityInput))
            selectedParticle.getVelocity().y = yVelocityInput.get();


        ImGui.setCursorPosX(20);
        ImGui.setCursorPosY(ImGui.getCursorPosY() + 10);
        ImGui.text("Movable");
        ImGui.sameLine();
        ImGui.setCursorPosX(85);

        if(ImGui.checkbox("##movable", movableInput))
            selectedParticle.setMovable(movableInput.get());


        ImGui.setCursorPosX(20);
        ImGui.setCursorPosY(ImGui.getCursorPosY() + 10);
        ImGui.text("Mass");
        ImGui.sameLine();
        ImGui.setCursorPosX(85);
        ImGui.setNextItemWidth(280);

        if(ImGui.inputDouble("##mass", massInput))
            selectedParticle.setMass(massInput.get());


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

        if(ImGui.sliderInt("##radius", radiusInput, 1, 100))
            selectedParticle.setRadius(radiusInput[0]);


        ImGui.setCursorPosX(20);
        ImGui.setCursorPosY(ImGui.getCursorPosY() + 10);

        if(ImGui.button("Delete", new ImVec2(345, 25))) {
            Main.particleHolder.removeParticle(Main.particleHolder.getSelected());
            Main.particleHolder.selectParticle(null);
        }


        ImGui.end();
    }
}
