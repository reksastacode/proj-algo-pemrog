# Sistem Antrean Restoran

Program Java untuk mencatat pesanan pelanggan restoran, mengurutkan antrean berdasarkan estimasi durasi masak, memproses pembayaran, dan menyimpan riwayat transaksi.

## Tentang Proyek

Sistem ini membantu petugas restoran mengelola antrean pesanan yang memiliki durasi masak berbeda-beda. Setiap pesanan dapat berisi beberapa menu sekaligus, lalu antrean diurutkan dari durasi tercepat ke terlama. Setelah pembayaran, data pesanan dipindahkan ke riwayat transaksi dan dapat dicari kembali berdasarkan ID.

Program ditulis secara prosedural menggunakan parallel array berukuran tetap (tanpa OOP).

## Fitur

1. **Tambah Pesanan** — satu pesanan dapat berisi beberapa menu, ID dibuat otomatis (`P1`, `P2`, ...).
2. **Tampilkan Antrean** — menampilkan seluruh pesanan aktif beserta rincian, durasi, dan total harga.
3. **Urutkan Antrean** — Bubble Sort ascending berdasarkan total durasi masak.
4. **Edit Pesanan** — mencari pesanan berdasarkan ID, lalu mengganti isi pesanan.
5. **Hapus Pesanan** — menghapus pesanan dari antrean berdasarkan ID.
6. **Proses Pembayaran** — konfirmasi pembayaran, membuat ID transaksi (`T1`, `T2`, ...), lalu mengeluarkan pesanan dari antrean.
7. **Riwayat Transaksi** — menampilkan seluruh transaksi yang sudah lunas.
8. **Cari Transaksi** — Linear Search berdasarkan ID transaksi.
9. **Keluar** — mengakhiri program.

## Struktur Data dan Algoritma

| Komponen | Implementasi |
|---|---|
| Data pesanan | 5 parallel array: `idPesanan`, `namaPelanggan`, `rincianPesanan`, `durasiPesanan`, `totalPesanan` |
| Data transaksi | 5 parallel array: `idTransaksi`, `idPesananTransaksi`, `namaPelangganTransaksi`, `rincianTransaksi`, `totalTransaksi` |
| Data menu | `namaMenu` (array 1D) dan `dataMenu` (array 2D: harga dan durasi masak) |
| Kapasitas | 100 pesanan dan 10 menu |
| Sorting | Bubble Sort ascending pada `durasiPesanan` |
| Searching | Linear Search pada ID pesanan dan ID transaksi |

## Cara Menjalankan

Prasyarat: JDK 8 atau lebih baru.

```powershell
javac SistemAntreanRestoran.java
java SistemAntreanRestoran
```

Program berjalan di terminal dan seluruh pilihan menu diisi melalui input angka.

## Pembagian Peran Tim

| Nama | Peran | Tanggung Jawab |
|---|---|---|
| Meiza Reksa Fahrevi | Project Leader & Lead Programmer | Menyusun analisis masalah, mengoordinasikan pengerjaan, mengintegrasikan dokumen teori dan praktikum, serta mengimplementasikan kode Java, fungsi modular, dan algoritma sorting/searching. |
| Putri Amallya Prastikha | Algorithm Designer | Merancang flowchart, skema alur perulangan, serta pemanggilan modul dan fungsi. |
| EM Nawwaf Adzka | Pseudocode & Tester | Menulis pseudocode terstruktur, menguji input tidak valid, serta melakukan debug dan error handling. |