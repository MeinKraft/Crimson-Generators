package crimsonfluff.crimsongenerators;

import com.mojang.blaze3d.matrix.MatrixStack;
import crimsonfluff.crimsongenerators.containers.GeneratorContainer;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GeneratorChestScreen extends ContainerScreen<GeneratorContainer> implements IHasContainer<GeneratorContainer> {
    public GeneratorChestScreen(GeneratorContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);

        this.passEvents = false;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.font.func_243248_b(matrixStack, new StringTextComponent(this.title.getString()), 8, 6.0F, 4210752);

        this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(), 8, (float) (166 - 93), 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bindTexture(new ResourceLocation(CrimsonGenerators.MOD_ID, "textures/gui/generator.png"));
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        //RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        blit(matrixStack, x, y, 0, 0, 176, 166, 256, 256);

        if (this.container.isBurning()) {
            int outPutted = this.container.itemsOutputted();
            int toOutput = this.container.itemsToOutput();

            //CrimsonGenerators.LOGGER.info("OUTPUT: " + toOutput);
            //blit(matrixStack, x + 79, y + 35, 176, 14, (int) f, 17, 256, 256);

            // NOTE: Should never be 0, but just in case
            if (toOutput != 0) {
                int f = outPutted * 14 / toOutput;

                blit(matrixStack, x + 57, y + 54 + f, this.container.isSoulFire() == 0 ? 176 : 190, f, 14, 14 - f, 256, 256);
            }
        }
    }
}
