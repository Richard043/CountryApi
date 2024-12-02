# CountryInfoAPP

Aplikasi ini memungkinkan pengguna untuk mendapatkan informasi tentang berbagai negara menggunakan API server pribadi.

# Download

Download the app: [countryinfo.apk](https://drive.google.com/uc?id=1Q32M4OaVxVvqpzjFD_ynpkXG96-rKBrZ)

---

## Langkah-langkah Menjalankan Proyek

1. **Jalankan Backend:**
   - Jalankan file backend `app.py`. Anda dapat melakukannya melalui Command Prompt (cmd) atau terminal:
     ```bash
     python app.py
     ```

2. **Server API:**
   - Server API pribadi akan berjalan di `localhost` pada alamat berikut:
     ```
     http://127.0.0.1:5000/
     ```

3. **Buka Proyek di Android Studio:**
   - Buka proyek `CountryInfoAPP` menggunakan Android Studio.

4. **Konfigurasi Base URL:**
   - Pada file `RetrofitClient.java`, Anda akan menemukan variabel `BASE_URL`. Secara default, URL tersebut mengarah ke server API saya yang sudah di-host secara publik.
   - Untuk menggunakan server API lokal Anda, ubah `BASE_URL` menjadi:
     ```java
     public static final String BASE_URL = "http://10.0.2.2:5000";
     ```

5. **Jalankan Aplikasi:**
   - Setelah konfigurasi selesai, jalankan aplikasi dari Android Studio untuk mencobanya.

---

## Video Demo

Klik pada link di bawah untuk melihat video demo aplikasi:

[Video Demo](https://drive.google.com/uc?id=11Zil7YhzU8JU-sgdyAveuhmloJDe20Lv)

---

Nikmati eksplorasi informasi negara dengan aplikasi ini! 😊
