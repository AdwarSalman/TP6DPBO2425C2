# Proyek Flappy Bird - TP6 DPBO

Ini adalah proyek implementasi game Flappy Bird sederhana menggunakan Java Swing. Proyek ini dibuat untuk memenuhi tugas praktikum mata kuliah DPBO.

Proyek ini mencakup semua fitur wajib, fitur bonus (menu utama), dan beberapa modifikasi kreatif pada *styling* GUI untuk meningkatkan pengalaman pengguna.

## 🗂️ Daftar Isi
* [Desain Program](#-desain-program)
  * [Struktur Kelas](#struktur-kelas)
  * [Desain Visual](#desain-visual)
* [Penjelasan Alur Program](#-penjelasan-alur-program)
  * [1. Startup (Menu Utama)](#1-startup-menu-utama)
  * [2. Memulai Permainan](#2-memulai-permainan)
  * [3. Gameplay (Loop Permainan)](#3-gameplay-loop-permainan)
  * [4. Game Over](#4-game-over)
  * [5. Restart Permainan](#5-restart-permainan)
* [Fitur yang Diimplementasikan](#-fitur-yang-diimplementasikan)
* [Dokumentasi (Demo Program)](#-dokumentasi-demo-program)

---

## 🎨 Desain Program

Program ini dirancang dengan memisahkan logika utama, data, dan tampilan.

### Struktur Kelas

Proyek ini terdiri dari 6 kelas utama:

* **`App.java` (Entry Point)**
    * Kelas ini berisi metode `main()` yang menjalankan aplikasi.
    * Bertanggung jawab untuk membuat `JFrame` utama (jendela aplikasi).
    * Berperan sebagai "manajer panel", yang mengatur panel mana yang sedang aktif (apakah `Startmenu` atau `FlappyBird`).
    * Menangani transisi dari menu ke game.

* **`Startmenu.java` (GUI Menu)**
    * Kelas yang mengatur tampilan visual untuk menu utama.
    * Menggunakan `BackgroundPanel` untuk menampilkan gambar latar.
    * Menggunakan `BoxLayout` untuk menyusun dua tombol secara vertikal ("Mulai Game" dan "Tutup Program").
    * Menerapkan *styling* kustom (warna latar, *font*, efek *hover*) pada kedua tombol.

* **`FlappyBird.java` (Logic & Game Panel)**
    * Ini adalah kelas inti yang berisi semua logika permainan.
    * Berperan sebagai `JPanel` tempat game digambar.
    * Mengimplementasikan `ActionListener` untuk meng-handle `Timer` (game loop).
    * Mengimplementasikan `KeyListener` untuk meng-handle input pemain (Spasi untuk lompat, 'R' untuk restart).
    * Mengelola dua `Timer`:
        1.  `gameLoop`: Berjalan ~60 FPS untuk pergerakan dan *rendering*.
        2.  `pipesCooldown`: Berjalan setiap 1.5 detik untuk memunculkan pipa baru.
    * Mengelola *state* permainan (skor, `isGameOver`).

* **`Player.java` (Data Model)**
    * Kelas *data model* (POJO) yang merepresentasikan pemain (burung).
    * Menyimpan properti pemain seperti posisi (x, y), ukuran (width, height), gambar, dan kecepatan vertikal (`velocityY`).

* **`Pipe.java` (Data Model)**
    * Kelas *data model* (POJO) yang merepresentasikan pipa.
    * Menyimpan properti pipa seperti posisi, ukuran, gambar, kecepatan horizontal, dan status `passed` (untuk logika skor).

* **`BackgroundPanel.java` (Utility GUI)**
    * Kelas `JPanel` kustom yang digunakan untuk menggambar gambar latar belakang.
    * Meng-override `paintComponent()` untuk menggambar `BufferedImage` agar pas di seluruh area panel.

### Desain Visual

Untuk memenuhi spesifikasi kreatif, *styling* GUI standar Swing telah dimodifikasi:

1.  **Tombol Menu Utama:**
    * Tombol "Mulai Game" dan "Tutup Program" tidak lagi polos.
    * Keduanya memiliki warna latar solid (Biru dan Merah), *font* kustom, dan *padding*.
    * Terdapat efek *hover* yang mengubah warna latar saat mouse menyentuh tombol, memberikan umpan balik visual yang jelas.

2.  **Dialog Game Over:**
    * Dialog `JDialog` *default* telah di-kustomisasi.
    * Menggunakan tema gelap (latar belakang abu-abu tua).
    * *Font* "Game Over!" diperbesar dan diwarnai putih.
    * Tombol "Restart" juga diberi *style* yang konsisten dengan tombol "Mulai Game" (biru dengan efek *hover*).

---

## ⚙️ Penjelasan Alur Program

Berikut adalah alur eksekusi program dari awal hingga akhir.

### 1. Startup (Menu Utama)

1.  Pengguna menjalankan program, metode `main()` di `App.java` dieksekusi.
2.  Sebuah `JFrame` dibuat.
3.  Objek `Startmenu` dibuat. Panel utamanya (`mainPanel`) ditambahkan ke `JFrame`.
4.  Pengguna melihat menu utama dengan gambar latar dan dua tombol: "Mulai Game" dan "Tutup Program".
5.  Jika pengguna menekan "Tutup Program", `System.exit(0)` dieksekusi dan aplikasi berhenti.

### 2. Memulai Permainan

1.  Pengguna menekan tombol "Mulai Game".
2.  `ActionListener` di `App.java` mendeteksi klik ini.
3.  Panel `Startmenu` dihapus dari `JFrame` (`frame.remove(startmenu.getMainPanel())`).
4.  Objek `FlappyBird` (panel game) dibuat dan ditambahkan ke `JFrame` (`frame.add(flappyBird)`).
5.  `frame.pack()` menyesuaikan ukuran frame, dan `flappyBird.requestFocus()` memastikan input keyboard langsung tertuju ke panel game.
6.  Konstruktor `FlappyBird` berjalan, `gameLoop` dan `pipesCooldown` (Timer) dimulai. Permainan dimulai.

### 3. Gameplay (Loop Permainan)

1.  **Game Loop:** `gameLoop` (diatur ke 1000/60 detik, sekitar 60 FPS) memicu `actionPerformed()`.
2.  **Input Pemain:**
    * Pemain menekan `SPACE`.
    * `keyPressed()` mendeteksi ini dan memeriksa `!isGameOver`.
    * `player.setVelocityY(-10)` diatur, memberi burung dorongan ke atas.
3.  **Pergerakan & Fisika:**
    * Setiap *tick* `actionPerformed()`, `move()` dipanggil.
    * `player.setVelocityY(player.getVelocityY() + gravity)`: Gravitasi (nilai 1) ditambahkan ke kecepatan vertikal, menarik burung ke bawah.
    * `player.setPosY(player.getPosY() + player.getVelocityY())`: Posisi Y burung diperbarui.
4.  **Generasi Pipa:**
    * Setiap 1.5 detik, `pipesCooldown` memanggil `placePipes()`.
    * Sepasang objek `Pipe` (atas dan bawah) dibuat dengan posisi Y acak dan ditambahkan ke `ArrayList pipes`.
5.  **Pergerakan Pipa & Skor:**
    * Di dalam `move()`, semua pipa di `ArrayList` digerakkan ke kiri (`pipe.setPosX(...)`).
    * Sistem memeriksa jika pipa belum dilewati (`!pipe.isPassed()`) dan posisi X-nya sudah di belakang pemain.
    * Jika ya, `score` bertambah +1 dan pipa ditandai `setPassed(true)` agar tidak dihitung dua kali.
6.  **Deteksi Tabrakan:**
    * Setiap *tick* `actionPerformed()`, `checkCollision()` dipanggil.
    * Program memeriksa *intersect* antara `Rectangle` pemain dan setiap `Rectangle` pipa.
    * Program juga memeriksa jika pemain menyentuh batas bawah atau atas layar.
7.  **Rendering:**
    * Setelah `move()` dan `checkCollision()`, `repaint()` dipanggil.
    * `paintComponent()` dieksekusi, yang memanggil `draw()`.
    * `draw()` menggambar ulang latar belakang, burung, semua pipa, dan skor (`g.drawString()`) di posisi terbaru mereka.

### 4. Game Over

1.  `checkCollision()` mendeteksi tabrakan.
2.  Metode `gameOver()` segera dipanggil.
3.  `gameLoop.stop()` dan `pipesCooldown.stop()` menghentikan semua pergerakan dan pembuatan pipa.
4.  Variabel `isGameOver` diatur ke `true`. Ini penting untuk menonaktifkan tombol `SPACE` di `keyPressed()`.
5.  Sebuah `JDialog` kustom (yang sudah di-styling) dimunculkan, menampilkan skor akhir, label "Press R for Restart", dan tombol "Restart".

### 5. Restart Permainan

Pemain dapat me-restart permainan dengan dua cara:

1.  **Menekan Tombol 'R' (di dialog atau jendela game):**
    * `keyPressed()` di `FlappyBird.java` (untuk jendela game) atau `KeyListener` di `gameOver()` (untuk dialog) mendeteksi 'R'.
    * Kode ini memeriksa `if (isGameOver)`.
    * Dialog ditutup (`dialog.dispose()`).
    * `resetGame()` dipanggil.
2.  **Menekan Tombol "Restart":**
    * `ActionListener` pada tombol "Restart" di dialog `gameOver()` dipicu.
    * Dialog ditutup (`dialog.dispose()`).
    * `resetGame()` dipanggil.

**Proses `resetGame()`:**
* Posisi pemain dikembalikan ke awal.
* `ArrayList pipes` dibersihkan (`pipes.clear()`).
* `score` di-reset ke 0.
* `isGameOver` diatur kembali ke `false`.
* `gameLoop` dan `pipesCooldown` di-`start()` lagi.

---

## 📋 Fitur yang Diimplementasikan

Berikut adalah *checklist* fitur dari spesifikasi tugas yang telah diimplementasikan:

### Spesifikasi Wajib
- [x] **Game Over:** Permainan berhenti saat menabrak pipa atau jatuh (di-handle di `checkCollision()`).
- [x] **Restart (Tombol 'R'):** Fitur restart diimplementasikan melalui `KeyListener` di `FlappyBird.java` dan di dalam dialog `gameOver()` yang memanggil `resetGame()`.
- [x] **Tampilan Skor (JLabel):** (Disesuaikan) Menggunakan `g.drawString()` untuk menampilkan skor *real-time* setelah konfirmasi Asprak.
- [x] **Skor Bertambah (+1):** Skor bertambah +1 setiap kali pemain berhasil melewati pipa (di-handle di `move()`).

### Spesifikasi Bonus
- [x] **GUI Form Awal:** Dibuat menggunakan `Startmenu.java` yang ditampilkan sebelum game.
- [x] **Dua Tombol (Mulai & Tutup):** Diimplementasikan di `Startmenu.java`.

### Spesifikasi Creative
- [x] **Modifikasi Kreatif:** Melakukan *styling* kustom pada tombol-tombol `Startmenu` dan dialog `gameOver()` agar memiliki tampilan yang lebih modern (tema gelap, efek *hover*) daripada komponen Swing standar.

---

## 📸 Dokumentasi (Demo Program)

https://github.com/user-attachments/assets/58862b74-5558-42f6-9db8-b711c34a4620


