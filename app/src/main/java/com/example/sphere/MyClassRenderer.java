package com.example.sphere;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyClassRenderer implements GLSurfaceView.Renderer{
    private Context context;
    public static float xposition,yposition,zposition;
    // направление взгляда камеры
    private float xlook,ylook,zlook;
    private float xtop,ytop,ztop;
    private float [] ambientmaterialArray={0.2f, 0.2f, 0.2f, 1f};
    // цвета отраженного рассеянного света
    private float [] diffusematerialArray={0.8f, 0.8f, 0.8f, 1f};
    // цвета отраженного зеркального света
    private float [] specularmaterialArray={0.5f, 0.5f, 0.5f,1f};
    // соответствующие им буферы цветов материала
    private FloatBuffer ambientmaterialBuffer,
            diffusematerialBuffer,specularmaterialBuffer;
    private float [] positionlightArray={0.5f,0,0.2f,0};
    // массивы для хранения цветов источника света
    // цвета общего фонового освещения
    private float [] ambientlightArray={0.5f, 0.5f, 0.5f, 1f};
    // цвета отраженного рассеянного света
    private float [] diffuselightArray={0.8f, 0.8f, 0.8f, 1f};
    // цвета отраженного зеркального света
    private float [] specularlightArray={0.8f, 0.8f, 0.8f,1f};
    // соответствующие им буферы источника света
    private FloatBuffer positionlightBuffer,ambientlightBuffer,
            diffuselightBuffer,specularlightBuffer;

    private Texture tex1,tex2, tex3;
    private Sphere sphere, sphere2, sphere3;
    float k = 2.0f;
    public MyClassRenderer(Context context) {
    this.context=context;
    xposition=1f;
    yposition=1f;
    zposition=4f;
    xlook = 0;
    ylook = 0;
    zlook = 0;
    xtop = 0;
    ytop = 1;
    ztop = 0;
    ByteBuffer b1 = ByteBuffer.allocateDirect(4 * 4);
    b1.order(ByteOrder.nativeOrder());
    ambientmaterialBuffer = b1.asFloatBuffer();
    ambientmaterialBuffer.put(ambientmaterialArray);
    ambientmaterialBuffer.position(0);

    ByteBuffer b2 = ByteBuffer.allocateDirect(4 * 4);
    b2.order(ByteOrder.nativeOrder());
    diffusematerialBuffer = b2.asFloatBuffer();
    diffusematerialBuffer.put(diffusematerialArray);
    diffusematerialBuffer.position(0);

    ByteBuffer b3 = ByteBuffer.allocateDirect(4 * 4);
    b3.order(ByteOrder.nativeOrder());
    specularmaterialBuffer = b3.asFloatBuffer();
    specularmaterialBuffer.put(specularmaterialArray);
    specularmaterialBuffer.position(0);

    ByteBuffer b4 = ByteBuffer.allocateDirect(4 * 4);
    b4.order(ByteOrder.nativeOrder());
    positionlightBuffer = b4.asFloatBuffer();
    positionlightBuffer.put(positionlightArray);
    positionlightBuffer.position(0);

    ByteBuffer b5 = ByteBuffer.allocateDirect(4 * 4);
    b5.order(ByteOrder.nativeOrder());
    ambientlightBuffer = b5.asFloatBuffer();
    ambientlightBuffer.put(ambientlightArray);
    ambientlightBuffer.position(0);

    ByteBuffer b6 = ByteBuffer.allocateDirect(4 * 4);
    b6.order(ByteOrder.nativeOrder());
    diffuselightBuffer = b6.asFloatBuffer();
    diffuselightBuffer.put(diffuselightArray);
    diffuselightBuffer.position(0);

    ByteBuffer b7 = ByteBuffer.allocateDirect(4 * 4);
    b7.order(ByteOrder.nativeOrder());
    specularlightBuffer = b7.asFloatBuffer();
    specularlightBuffer.put(specularlightArray);
    specularlightBuffer.position(0);

    sphere = new Sphere(0.5f);
    sphere2 = new Sphere(0.3f);
    sphere3 = new Sphere(0.1f);
    }    

    float p;
    boolean check = true;
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);


        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        GLU.gluLookAt(gl, xposition,yposition,zposition, xlook,ylook,zlook, xtop,ytop,ztop);

        gl.glRotatef(p,0,1,0);
        sphere.draw(gl);

        gl.glTranslatef(0.5f, 0.5f, 0.5f);

        gl.glEnable(GL10.GL_LIGHT0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionlightBuffer);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientlightBuffer);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuselightBuffer);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularlightBuffer);

        sphere2.draw(gl);
        gl.glTranslatef(0.5f, 0f, -0.2f);
        sphere3.draw(gl);

        if ( k > 0 && check)k -= 0.010f;
        else check = false;
        if(k < 5 && !check) k +=  0.010f;
        else check = true;

        p=(p>360)?0:p+1f;
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective (gl, 60, ratio, 0.1f, 100f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glEnable(GL10.GL_NORMALIZE);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glEnable(GL10.GL_LIGHTING);
        tex1=new Texture(gl,context, R.drawable.sun);
        tex2=new Texture(gl,context, R.drawable.zem);
        tex3=new Texture(gl,context, R.drawable.luna);
        sphere.setTextureName(tex1.getName());
        sphere2.setTextureName(tex2.getName());
        sphere3.setTextureName(tex3.getName());
        gl.glClearColor(222.0f / 255.0f, 222.0f / 255.0f, 222.0f / 255.0f, 1.0f);
    }
}
