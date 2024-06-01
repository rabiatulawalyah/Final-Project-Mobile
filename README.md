Deskripsi Aplikasi
Nama Aplikasi: ALienWorlds

Deskripsi Singkat: ALienWorlds adalah aplikasi edukatif dan interaktif yang memberikan informasi rinci tentang planet-planet dalam tata surya dan di luar tata surya, saya kasih nama itu karena planet-planet lain sering dikaitkan dengan keberadaan alien. 
Aplikasi ini menyediakan deskripsi, massa, dan volume setiap planet, serta galeri foto dokumentasi. Dengan tema alien yang menarik, aplikasi ini memberikan pengalaman belajar yang menyenangkan dan informatif.

Cara Penggunaan:
Buka Aplikasi: Luncurkan ALienWorlds di perangkat Anda.
Daftar Planet: Pada halaman utama (main screen), Anda akan melihat daftar planet. Setiap planet ditampilkan dengan nama dan gambar mini.
Pilih Planet: Ketuk pada nama atau gambar mini planet yang ingin Anda pelajari lebih lanjut.
Detail Planet: Anda akan dibawa ke halaman detail planet, yang menampilkan deskripsi planet, massa, volume, dan galeri foto. Informasi tentang kemungkinan keberadaan alien di planet tersebut juga disertakan.
Navigasi: Gunakan tombol navigasi atau swipe gesture untuk beralih ke photos yang berisi dokumentasi planet

Implementasi Teknis
Struktur Aplikasi:

Package dan Import:
package com.example.finalproject.activities;
Mengimport berbagai library dan kelas yang diperlukan untuk mengimplementasikan fungsionalitas aplikasi.

Komponen UI:
Menggunakan Button, EditText, ImageView, LinearLayout, TextView, dan LottieAnimationView untuk membangun antarmuka pengguna.
Layout ditentukan dalam file XML (misalnya, activity_login.xml) yang berada di folder res/layout.

Koneksi Jaringan:
Memeriksa ketersediaan jaringan dengan ConnectivityManager dan NetworkInfo.

Database:
Menggunakan SQLite untuk menyimpan dan mengelola data pengguna dan status login.
DBHelper adalah kelas helper yang memperluas SQLiteOpenHelper untuk membuat, mengelola, dan mengupgrade database.

Multithreading:
Menggunakan ExecutorService untuk menjalankan tugas-tugas berat di background thread agar UI tetap responsif.

APi: 
Saya menggunakan api dari rapidapi (https://rapidapi.com/newbAPIOfficial/api/planets-info-by-newbapi/)
