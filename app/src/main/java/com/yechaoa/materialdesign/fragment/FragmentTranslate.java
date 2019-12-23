package com.yechaoa.materialdesign.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yechaoa.materialdesign.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;


public class FragmentTranslate extends Fragment {
    public static final int TAKE_PHOTO = 1;

    private Uri imageUri;
    private String imgStr;
    private String imgParam;

    //okhttp
    private static String subscriptionKey = "a872995b97074a298f4adb299af2f121";
    private static String endpoint = "https://api.cognitive.microsofttranslator.com";
    String url = endpoint + "/translate?api-version=3.0&to=en";
    String url_camera = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic?access_token=24.81f83879b920f27248975b092df79192.2592000.1579696859.282335-18081136";


    OkHttpClient client = new OkHttpClient.Builder()
            .protocols(Collections.singletonList(Protocol.HTTP_1_1))
            .build();
    OkHttpClient client_camera = new OkHttpClient.Builder()
            .build();


    private EditText et_translate;
    private TextView tv_translate_result;
    private String translate_result;
    private ImageButton btn_text_translate, btn_camera, btn_copy_result;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_translate, null);

        return view;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //okhttp
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        et_translate=(EditText)view.findViewById(R.id.translateBlock);
        tv_translate_result = (TextView)view.findViewById(R.id.tv_translate_result);
        btn_text_translate=(ImageButton)view.findViewById(R.id.btn_text_translate);
        btn_copy_result = (ImageButton)view.findViewById(R.id.btn_copy_result);
        btn_camera = (ImageButton)view.findViewById(R.id.btn_camera);

        btn_text_translate.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                String raw = et_translate.getText().toString().trim();
                Toast.makeText(getActivity(), raw, Toast.LENGTH_LONG).show();

                //返回的翻译文本魏Response，是一个经过prettify函数转化为字符串的翻译结果
                try {
                    String response = Post(raw);
                    translate_result = prettify(response);

                    tv_translate_result.setText(translate_result);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });


        btn_camera.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v) {
                // 创建File对象，用于存储拍照后的图片
                File outputImage = new File(getActivity().getExternalCacheDir(), "output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT < 24) {
                    imageUri = Uri.fromFile(outputImage);
                } else {
                    imageUri = FileProvider.getUriForFile(getActivity(), "com.yechaoa.materialdesign.fileprovider", outputImage);
                }
                // 启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);

            }
        });


        btn_copy_result.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                copyText(translate_result);
            }
        });

    }

    public String Post(String text) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "[{\n\t\"Text\": \""+text+"\"\n}]");
        Request request = new Request.Builder()
                .url(url).post(body)
                .addHeader("Ocp-Apim-Subscription-Key", subscriptionKey)
                .addHeader("Content-type", "application/json").build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private void copyText(String copiedText) {
        ClipboardManager clipboardManager = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, copiedText));

//        getActivity().showToast("Copy Text: " + copiedText);
    }

    public static String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(json_text);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String Response=gson.toJson(json);
        JsonArray jsonArray=parser.parse(Response).getAsJsonArray();

        JsonObject jsonObject=jsonArray.get(0).getAsJsonObject();
        JsonArray  translation=jsonObject.get("translations").getAsJsonArray();

        String txt = translation.get(0).getAsJsonObject().get("text").getAsString();
        return txt;
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    getActivity().openAlbum();
//                } else {
//                    Toast.makeText(getActivity(), "You denied the permission", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//        }
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
//                        picture.setImageBitmap(bitmap);
                        imgStr=bitmapToString(bitmap);
                        imgParam = URLEncoder.encode(imgStr, "UTF-8");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        String response = Post_Camera(imgParam);
                        //Todo:将response转化为字符串
                        String temp = "-------Take a Picture------------";
                        et_translate.setText(temp);
                    } catch (Exception e) {
                        System.out.println(e);
                    }


                }
                break;
//            case CHOOSE_PHOTO:
//                if (resultCode == RESULT_OK) {
//                    // 判断手机系统版本号
//                    if (Build.VERSION.SDK_INT >= 19) {
//                        // 4.4及以上系统使用这个方法处理图片
//                        handleImageOnKitKat(data);
//                    } else {
//                        // 4.4以下系统使用这个方法处理图片
//                        handleImageBeforeKitKat(data);
//                    }
//                }
//                break;
            default:
                break;
        }
    }

    private String bitmapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public String Post_Camera(String img) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "image="+img);
        Request request = new Request.Builder()
                .url(url_camera).post(body)
                .addHeader("Content-type", "application/x-www-form-urlencoded").build();
        Response response = client_camera.newCall(request).execute();
        return response.body().string();
    }

}
