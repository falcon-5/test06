package com.example.texturesample;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.Bundle;
import android.view.Window;

public class TextureSample extends Activity
	implements GLSurfaceView.Renderer
{
	//GLSurfaceView
	private GLSurfaceView gLSurfaceView;
	
	//読み込んだテキスチャのID
	private int texID;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        //タイトルバーを消す
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //GLSurfaceViewを作成
        gLSurfaceView = new GLSurfaceView(this);
        gLSurfaceView.setRenderer(this);
        
        //レイアウトのリソース参照は渡さず直接Viewオブジェクトを渡す
        //setContentView(R.layout.main);
        setContentView(gLSurfaceView);
        
        //メンバの初期化
        texID = -1;
    }
    
    //描画のために毎フレーム呼び出される
    public void onDrawFrame(GL10 gl)
    {
    	//描画用バッファをクリア
    	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    	
    	//ポリゴンの描画メソッドを呼ぶ
    	TextureDrawer.drawTexture(gl, texID, 160.0f, 240.0f, 128, 128, 30.0f, 1.0f, 1.0f);
    }
    
    //サーフェイスのサイズ変更時に呼び出される
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
    	//ビューポートをサイズに合わせてセットし直す
    	gl.glViewport(0, 0, width, height);
    	
    	//射影行列を選択
    	gl.glMatrixMode(GL10.GL_PROJECTION);
    	//現在選択されている行列（射影行列）に、単位行列をセット
    	gl.glLoadIdentity();
    	//平行投影用のパラメータをセット
    	GLU.gluOrtho2D(gl, 0.0f, width, 0.0f, height);
    }
    
    //サーフェイスが生成される際・または再生成される際に呼び出される
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
    	//ディザを無効化
    	gl.glDisable(GL10.GL_DITHER);
    	
    	//カラーとテクスチャ座標の補間精度を、最も効率的なものに指定
    	gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
    	
    	//バッファ初期化時のカラー情報をセット
    	gl.glClearColor(0, 0, 0, 1);
    	
    	//片面表示を有効に
    	gl.glEnable(GL10.GL_CULL_FACE);
    	//カリング設定をCCWに
    	gl.glFrontFace(GL10.GL_CCW);
    	
    	//深度テストを無効に
    	gl.glDisable(GL10.GL_DEPTH_TEST);
    	
    	//フラットシェーディングにセット
    	gl.glShadeModel(GL10.GL_FLAT);
    	
    	//リソース読み込み
    	texID = TextureLoader.loadTexture(gl, this, R.drawable.droid);
    	
    }
    
    //ポーズ状態からの復旧時やアクティビティ生成時などに呼び出される
    protected void onResume()
    {
    	super.onResume();
    	gLSurfaceView.onResume();
    }
    
    //アクティビティ一時停止や終了時に呼び出される
    protected void onPause()
    {
    	super.onPause();
    	gLSurfaceView.onPause();
    }
}