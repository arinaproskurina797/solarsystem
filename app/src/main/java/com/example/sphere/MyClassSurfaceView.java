package com.example.sphere;
import android.content.Context;
import android.opengl.GLSurfaceView;
public class MyClassSurfaceView extends GLSurfaceView{

    private MyClassRenderer renderer;
    public MyClassSurfaceView(Context context) {
        super(context);
        renderer = new MyClassRenderer(context);
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}