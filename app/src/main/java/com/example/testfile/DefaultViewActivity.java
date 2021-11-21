/*
 *  Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.
 *  This subclass is created by HMS Core Toolkit 
 *  and used to receive token information or messages returned by HMS server
 *
 */
package com.example.testfile;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast

;import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;

public class DefaultViewActivity extends Activity {
    public static final int DEFAULT_VIEW = 0x22;
    private static final int REQUEST_CODE_SCAN = 0X01;
    TextView textView;
    Button copy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);
        copy =(Button)  findViewById(R.id.Copy);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                    DEFAULT_VIEW);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissions == null || grantResults == null || grantResults.length < 2 || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (requestCode == DEFAULT_VIEW) {
            //start ScankitActivity for scanning barcode
            ScanUtil.startScan(DefaultViewActivity.this, REQUEST_CODE_SCAN, new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE).create());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //receive result after your activity finished scanning
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        // Obtain the return value of HmsScan from the value returned by the onActivityResult method by using ScanUtil.RESULT as the key value.
        if (requestCode == REQUEST_CODE_SCAN) {
            Object obj = data.getParcelableExtra(ScanUtil.RESULT);
            if (obj instanceof HmsScan) {
                if (!TextUtils.isEmpty(((HmsScan) obj).getOriginalValue())) {
                    Toast.makeText(this, ((HmsScan) obj).getOriginalValue(), Toast.LENGTH_LONG).show();
                    textView =(TextView) findViewById(R.id.ketqua);
                    textView.setText(((HmsScan) obj).getOriginalValue());
                    copy =(Button)  findViewById(R.id.Copy);
                    copy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipboardManager clipboard =(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip =ClipData.newPlainText("QRcode", ((HmsScan) obj).getOriginalValue());
                            clipboard.setPrimaryClip(clip);

                            Toast.makeText(DefaultViewActivity.this, "Copied "+ ((HmsScan) obj).getOriginalValue(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                return;
            }
        }
    }
}
