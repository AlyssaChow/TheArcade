package com.example.TheArcade;

public class SpriteCollisionEvent {
    public Spritetank sprite;
    public Spritetank collidedSprite;

    public SpriteCollisionEvent(Spritetank sprite, Spritetank collidedSprite) {
        this.sprite = sprite;
        this.collidedSprite = collidedSprite;
    }
}
