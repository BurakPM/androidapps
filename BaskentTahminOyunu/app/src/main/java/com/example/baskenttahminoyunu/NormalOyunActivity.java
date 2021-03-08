package com.example.baskenttahminoyunu;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.Random;

public class NormalOyunActivity extends AppCompatActivity {
    private static final String TAG = "NormalOyunActivity";

    private TextView mTextViewİpucu;
    private TextView mTextViewHarfler;
    private EditText mEditTextTahmin;
    private TextView mTextViewSkor;
    private TextView mTextViewGeriSayım;

    private final Random rng = new Random();
    private CountDownTimer timer;
    private int randomIndex;
    private String gelenSehir;
    private int harfSayisi;
    private String girilenTahmin;
    private StringBuilder şehirBoyutu;
    private ArrayList<Character> harfler;
    private int score;
    private final long toplamSure = 60000;


    private String[] şehirler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        şehirler = getResources().getStringArray(R.array.baskentler_array);

        setContentView(R.layout.activity_normal_oyun);

        mTextViewİpucu = findViewById(R.id.text_view_ipucu);
        mTextViewHarfler = findViewById(R.id.text_view_harfler);
        mEditTextTahmin = findViewById(R.id.edit_text_sehir_tahmini);
        mTextViewSkor = findViewById(R.id.text_view_score);
        mTextViewGeriSayım = findViewById(R.id.text_view_geri_sayım);

        zamalanayıcıyıBaşlat();

        rastgeleSehirAl();

        harfSayisiAyarla();

    }

    public void tahminEt(View view) {
        String rawTahmin = mEditTextTahmin.getText().toString();
        tahminDüzenle(rawTahmin);

        if (girilenTahmin == null || rawTahmin.equals("")) {
            showStyleableToast(R.string.bilgi_boş_tahmin);
            return;
        }

        if (girilenTahmin.equals(gelenSehir)) {
            showStyleableToast(R.string.bilgi_tebrikler);
            mEditTextTahmin.setText("");

            rastgeleSehirAl();
            harfSayisiAyarla();
            zamalanayıcıyıBaşlat();

        } else {


            if (score >= 25)
                showStyleableToast(R.string.bilgi_yanlış_tahmin);

            skoruAzalt(harfSayisi);

        }
    }

    public void harfAl(View view) {
        int randHarfIndex;
        şehirBoyutu = new StringBuilder();

        if (harfler.size() > 0) {
            randHarfIndex = rng.nextInt(harfler.size());

            String[] tViewHarfler = mTextViewHarfler.getText().toString().split(" ");
            char[] gelenHarfler = gelenSehir.toCharArray();

            for (int i = 0; i < gelenSehir.length(); i++) {
                if (tViewHarfler[i].equals("_") && gelenHarfler[i] == harfler.get(randHarfIndex)) {
                    tViewHarfler[i] = String.valueOf(harfler.get(randHarfIndex));

                    for (int j = 0; j < gelenSehir.length(); j++) {

                        if (j < gelenSehir.length() - 1) {
                            şehirBoyutu.append(tViewHarfler[j]).append(" ");
                        } else {
                            şehirBoyutu.append(tViewHarfler[j]);
                        }
                    }
                    break;
                }

            }
            harfler.remove(randHarfIndex);
            mTextViewHarfler.setText(şehirBoyutu);

            skoruAzalt(harfSayisi);

        }

    }


    private void tahminDüzenle(String tahmin) {
        String bosluksuzTahmin = tahmin.trim();

        if (bosluksuzTahmin.length() >= 1) {
            girilenTahmin = bosluksuzTahmin
                    .substring(0, 1).toUpperCase()
                    + bosluksuzTahmin.substring(1).toLowerCase();


        }
    }

    private void harfSayisiAyarla() {
        StringBuilder harfÇizgisi = new StringBuilder();
        for (int i = 0; i < harfSayisi; i++) {
            if (i < harfSayisi - 1) {
                harfÇizgisi.append("_ ");
            } else {
                harfÇizgisi.append("_");
            }
        }
        mTextViewHarfler.setText(harfÇizgisi.toString());

    }

    private void rastgeleSehirAl() {
        mEditTextTahmin.setText("");

        randomIndex = rng.nextInt(şehirler.length);
        gelenSehir = şehirler[randomIndex];
        harfSayisi = gelenSehir.length();
        harfler = new ArrayList<>();

        score = 100;
        mTextViewSkor.setText(getString(R.string.bilgi_skorunuz, score));

        mTextViewİpucu.setText(getString(R.string.bilgi_harf_sayısı, harfSayisi));

        for (char c : gelenSehir.toCharArray()) {
            harfler.add(c);
        }
    }

    private void zamalanayıcıyıBaşlat() {
        if (timer != null) {
            timer.cancel();
            score = 100;

        } else {
            zamanlayıcıAyarla();

        }

        timer.start();
    }

    private void skoruAzalt(int şehirUzunluğu) {
        if (score >= 25) {

            if (şehirUzunluğu < 5) {
                score -= 40;
            } else if (şehirUzunluğu >= 5 && şehirUzunluğu <= 7) {
                score -= 30;
            } else if (şehirUzunluğu >= 7 && şehirUzunluğu <= 10) {
                score -= 20;
            } else if (şehirUzunluğu >= 10 && şehirUzunluğu <= 15) {
                score -= 10;
            } else {
                score -= 5;
            }

            mTextViewSkor.setText(getString(R.string.bilgi_skorunuz, score));
        } else {
            //showStyleableToast(R.string.bilgi_başarısız);
            StyleableToast.makeText(this, gelenSehir, Toast.LENGTH_SHORT, R.style.customtoast)
                    .show();

            rastgeleSehirAl();
            harfSayisiAyarla();
            zamalanayıcıyıBaşlat();
        }
    }

    private void zamanlayıcıAyarla() {
        score = 100;

        timer = new CountDownTimer(toplamSure, 1000) {

            @Override
            public void onTick(long ms) {
                mTextViewGeriSayım.setText(((ms / 60000) + ":" + ((ms % 60000) / 1000)));
            }

            @Override
            public void onFinish() {
                mTextViewGeriSayım.setText("0:00");
                // showStyleableToast(R.string.bilgi_süre_doldu);

                zamalanayıcıyıBaşlat();

                rastgeleSehirAl();

                harfSayisiAyarla();
            }
        };
    }

    private void showStyleableToast(int res) {
        StyleableToast.makeText
                (this, getString(res), R.style.customtoast)
                .show();

    }


}
