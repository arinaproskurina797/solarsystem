package com.example.sphere;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;


public class Sphere {
    private FloatBuffer mVertexBuffer;
    private FloatBuffer textureBuffer;
    int n = 0;
    private int textureName;

    public Sphere(float R) {
        int dtheta=15,dphi=15;

        int theta,phi;
        float DTOR=(float) (Math.PI/180.0f);

        ByteBuffer byteBuf = ByteBuffer.allocateDirect(5000*3*4);
        byteBuf.order(ByteOrder.nativeOrder());
        mVertexBuffer = byteBuf.asFloatBuffer();
        byteBuf = ByteBuffer.allocateDirect(5000*2*4);
        byteBuf.order(ByteOrder.nativeOrder());
        textureBuffer=byteBuf.asFloatBuffer();
        for (theta=-90;theta<=90-dtheta;theta+=dtheta) {
    for (phi=0;phi<=360-dphi;phi+=dphi){

    mVertexBuffer.put((float)(Math.cos(theta*DTOR) * Math.cos(phi*DTOR))*R);
    mVertexBuffer.put((float)(Math.cos(theta*DTOR) * Math.sin(phi*DTOR))*R);
    mVertexBuffer.put((float)(Math.sin(theta*DTOR))*R);

    mVertexBuffer.put((float)(Math.cos((theta+dtheta)*DTOR) * Math.cos(phi*DTOR))*R);
    mVertexBuffer.put((float)(Math.cos((theta+dtheta)*DTOR) * Math.sin(phi*DTOR))*R);
    mVertexBuffer.put((float)(Math.sin((theta+dtheta)*DTOR))*R);

    mVertexBuffer.put((float)(Math.cos((theta+dtheta)*DTOR) * Math.cos((phi+dphi)*DTOR))*R);
    mVertexBuffer.put((float)(Math.cos((theta+dtheta)*DTOR) * Math.sin((phi+dphi)*DTOR))*R);
    mVertexBuffer.put((float)(Math.sin((theta+dtheta)*DTOR))*R);
    n+=3;

    mVertexBuffer.put((float)(Math.cos(theta*DTOR) * Math.cos((phi+dphi)*DTOR))*R);
    mVertexBuffer.put((float)(Math.cos(theta*DTOR) * Math.sin((phi+dphi)*DTOR))*R);
    mVertexBuffer.put((float)(Math.sin(theta*DTOR))*R);
    n++;
    textureBuffer.put((float)(phi/360.0f));
    textureBuffer.put((float)((90+theta)/180.0f));

    textureBuffer.put((float)(phi/360.0f));
    textureBuffer.put((float)((90+theta+dtheta)/180.0f));

    textureBuffer.put((float)((phi+dphi)/360.0f));
    textureBuffer.put((float)((90+theta+dtheta)/180.0f));

    textureBuffer.put((float)((phi+dphi)/360.0f));
    textureBuffer.put((float)((90+theta)/180.0f));
}
}        mVertexBuffer.position(0);
        textureBuffer.position(0);
    }

    public void setTextureName(int name){
        textureName=name;
    }

    public void draw(GL10 gl) {
        gl.glFrontFace(GL10.GL_CCW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA,GL10.GL_ONE_MINUS_SRC_ALPHA);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);

        if (textureName!=0){
            gl.glEnable(GL10.GL_TEXTURE_2D);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textureName);
            gl.glTexEnvx(GL10.GL_TEXTURE_ENV,
                    GL10.GL_TEXTURE_ENV_MODE,
                    GL10.GL_MODULATE);
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glTexCoordPointer(2,GL10.GL_FLOAT,0,textureBuffer);
        }else{
            gl.glDisable(GL10.GL_TEXTURE_2D);
        }

        for (int i = 0; i < n; i += 4)
            gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, i,4 );
    }
}
