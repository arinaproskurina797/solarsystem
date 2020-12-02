package com.example.sphere;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;


public class Texture {
    private int name;
    public Texture(GL10 gl, Context context, int idpicture){
        int[] names=new int[1];
        gl.glGenTextures(1, names, 0);
        name=names[0];
        gl.glPixelStorei(GL10.GL_UNPACK_ALIGNMENT,1);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, name);
        gl.glTexParameterx(GL11.GL_TEXTURE_2D,
                GL11.GL_GENERATE_MIPMAP, GL11.GL_TRUE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MIN_FILTER,
                GL10.GL_LINEAR_MIPMAP_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MAG_FILTER,
                GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);        
        gl.glTexParameterx(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_WRAP_T,
                GL10.GL_REPEAT);
        Bitmap bitmap = BitmapFactory.decodeResource
                (context.getResources(),idpicture);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
    }
    public int getName(){
        return name;
    }
}
