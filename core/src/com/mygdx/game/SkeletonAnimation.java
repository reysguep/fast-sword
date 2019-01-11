package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonMeshRenderer;

/**
 *
 * @author reysguep
 */
public class SkeletonAnimation {

    public final Skeleton skeleton;
    private final AnimationState state;
    private TrackEntry track;
    private float width, height;

    boolean isReversed;
    String currentAnimation;

    public SkeletonAnimation(SkeletonData data) {
        skeleton = new Skeleton(data);
        AnimationStateData stateData = new AnimationStateData(data);
        state = new AnimationState(stateData);

        width = data.getWidth();
        height = data.getHeight();
        isReversed = false;
    }

    public SkeletonAnimation(SkeletonData data, String animationName) {
        this(data);
        state.setAnimation(0, animationName, false);
        currentAnimation = animationName;
    }

    public static SkeletonData createSkeletonData(String folder) {
        TextureAtlas atlas;
        SkeletonJson json;
        SkeletonData data;

        atlas = new TextureAtlas(Gdx.files.internal(folder + "map.atlas"));
        json = new SkeletonJson(atlas);
        data = json.readSkeletonData(Gdx.files.internal(folder + "data.json"));

        return data;
    }

    public float getX() {
        float x;
        x = (skeleton.getX());

        return x;
    }

    public float getY() {
        float y;
        y = (skeleton.getY());

        return y;
    }

    public boolean isReversed() {
        return isReversed;
    }

    public void setAnimation(String animationName, boolean loop) {
        track = state.setAnimation(0, animationName, loop);
        currentAnimation = animationName;
        isReversed = false;
    }

    public void setPosition(float x, float y) {
        skeleton.setPosition(x, y);
    }

    public void flipX(boolean flipX) {
        boolean flipY;

        flipY = skeleton.getFlipY();
        flip(flipX, flipY);
    }

    public void flipY(boolean flipY) {
        boolean flipX;

        flipX = skeleton.getFlipX();
        flip(flipX, flipY);
    }

    public void flip(boolean flipX, boolean flipY) {
        skeleton.setFlip(flipX, flipY);
    }

    public void draw(SkeletonMeshRenderer renderer, PolygonSpriteBatch batch) {
        state.update(Gdx.graphics.getDeltaTime());
        state.apply(skeleton);
        skeleton.updateWorldTransform();

        renderer.draw(batch, skeleton);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public boolean equals(String animationName) {
        return currentAnimation.equals(animationName);
    }

    public void setColor(Color color) {
        skeleton.setColor(color);
    }

    public void setAlpha(float alpha) {
        Color color = skeleton.getColor();
        color.a = alpha;
    }

    public boolean isAnimationFinished() {
        return isReversed ^ track.isComplete();
    }


    public void reverseAnimation(boolean reverse) {
        if (reverse == true) {
            track.setTimeScale(-1f);
            track.setAnimationEnd(0);
            track.setAnimationStart(track.getAnimation().getDuration());
            track.setLoop(true);
        } else {
            track.setTimeScale(1);
            track.setAnimationEnd(track.getAnimation().getDuration());
            track.setAnimationStart(0);
        }
        
        isReversed = reverse;
    }

    public AnimationState getSkeletonState() {
        return state;
    }

    public TrackEntry getTrack() {
        return track;
    }

    public void setSize(float width, float height) {
        float wScale = width / this.width;
        float hScale = height / this.height;
        setScale(wScale, hScale);
    }

    public void setScale(float scale) {
        skeleton.getRootBone().setScale(scale);
    }

    public void setScale(float scaleWidth, float scaleHeight) {
        skeleton.getRootBone().setScale(scaleWidth, scaleHeight);
    }
}
