package scenes;


import components.GridLines;
import components.MouseControls;
import components.Sprite;
import components.SpriteSheet;
import imgui.ImGui;
import imgui.ImVec2;
import jade.Camera;
import jade.GameObject;
import jade.Prefabs;
import jade.Transform;
import org.joml.Vector2f;
import org.joml.Vector3f;
import renderer.DebugDraw;
import util.AssetPool;

// For editing Level
public class LevelEditorScene extends Scene {

    private GameObject obj1;
    private SpriteSheet sprites;

    GameObject levelEditorStuff = new GameObject("LevelEditor", new Transform(new Vector2f()), 0);

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        levelEditorStuff.addComponent(new MouseControls());
        levelEditorStuff.addComponent(new GridLines());

        loadResources();

       this.camera = new Camera(new Vector2f(-250, 0));
       sprites = AssetPool.getSpritesheet("assets/images/spritesheets/decorationsAndBlocks.png");

       if (levelLoaded) {
           this.activeGameObject = gameObjects.get(0);
           return;
       }


//       obj1 = new GameObject("Object 1",
//               new Transform(new Vector2f(200, 100), new Vector2f(256, 256)), 2);
//       SpriteRenderer obj1SpriteRenderer = new SpriteRenderer();
//       obj1SpriteRenderer.setColor(new Vector4f(1, 0, 0, 1));
//       obj1.addComponent(obj1SpriteRenderer);
//       obj1.addComponent(new Rigidbody());
//       this.addGameObjectToScene(obj1);
//       this.activeGameObject = obj1;
//
//        GameObject obj2 = new GameObject("Object 2",
//                new Transform(new Vector2f(400, 100), new Vector2f(256, 256)), 4);
//        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
//        Sprite obj2Sprite = new Sprite();
//        obj2Sprite.setTexture(AssetPool.getTexture("assets/images/blendImage2.png"));
//        obj2SpriteRenderer.setSprite(obj2Sprite);
//        obj2.addComponent(obj2SpriteRenderer);
//        this.addGameObjectToScene(obj2);



    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpriteSheet("assets/images/spritesheets/decorationsAndBlocks.png",
                new SpriteSheet(AssetPool.getTexture(("assets/images/spritesheets/decorationsAndBlocks.png")),
                        16, 16, 81, 0));
        AssetPool.getTexture("assets/images/blendImage2.png");
    }

    float x = 0.0f;
    float y = 0.0f;

    @Override
    public void update(float dt) {
        levelEditorStuff.update(dt);
        DebugDraw.addBox2D(new Vector2f(400, 200), new Vector2f(64, 32), 30, new Vector3f(0, 1,0), 1);
        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.begin("Test window");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.getSprite(i);
            float spriteWidth = sprite.getWidth() * 4;
            float spriteHeight = sprite.getHeight() * 4;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y,
                    texCoords[0].x, texCoords[2].y)) {
                GameObject object = Prefabs.generateSpriteObject(sprite, 32, 32);
                levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if (i + 1 < sprites.size() && nextButtonX2 < windowX2) {
                ImGui.sameLine();
            }
        }

        ImGui.end();
    }

}
