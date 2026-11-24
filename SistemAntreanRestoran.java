import java.util.Scanner;

public class SistemAntreanRestoran {
    static final int MAX_MENU = 5;
    static final int MAX_DATA = 50;

    // Master Menu
    static String[] namaMenu = {
        "Nasi Goreng", "Mie Goreng", "Ayam Bakar", "Soto Ayam", "Es Teh Manis"
    };
    static int[][] infoMenu = {
        {18000, 15},
        {15000, 10},
        {25000, 25},
        {16000, 12},
        {5000,  3}
    };

    // Parallel Array Antrean Aktif
    static String[] idPesanan = new String[MAX_DATA];
    static String[] namaPelanggan = new String[MAX_DATA];
    static String[] menuPesanan = new String[MAX_DATA];
    static int[] jumlahPorsi = new int[MAX_DATA];
    static int[] totalHarga = new int[MAX_DATA];
    static int[] durasiMasak = new int[MAX_DATA];

    // Parallel Array Riwayat Transaksi
    static String[] idTransaksi = new String[MAX_DATA];
    static String[] trxIdPesanan = new String[MAX_DATA];
    static String[] trxPelanggan = new String[MAX_DATA];
    static String[] trxMenu = new String[MAX_DATA];
    static int[] trxTotalBayar = new int[MAX_DATA];

    // Counter & Penanda Ukuran
    static int jumlahAntrean = 0;
    static int jumlahRiwayat = 0;
    static int counterOrder = 0;
    static int counterTrx = 0;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Self-check logic sorting & searching
        jalankanSelfCheck();

        int pilihan;
        do {
            tampilkanMenu();
            System.out.print("Pilih menu (0-7): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Input harus angka!");
                scanner.next();
                System.out.print("Pilih menu (0-7): ");
            }
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    tambahPesanan();
                    break;
                case 2:
                    tampilAntrean();
                    break;
                case 3:
                    urutkanAntrean();
                    break;
                case 4:
                    editPesanan();
                    break;
                case 5:
                    hapusPesanan();
                    break;
                case 6:
                    prosesPembayaran();
                    break;
                case 7:
                    menuRiwayatTransaksi();
                    break;
                case 0:
                    System.out.println("Program selesai.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 0);
    }

    static void tampilkanMenu() {
        System.out.println("\n====================================");
        System.out.println("      SISTEM ANTREAN RESTORAN       ");
        System.out.println("====================================");
        System.out.println("1. Tambah Pesanan");
        System.out.println("2. Tampilkan Antrean");
        System.out.println("3. Urutkan Antrean (Durasi Masak)");
        System.out.println("4. Edit Pesanan");
        System.out.println("5. Hapus Pesanan");
        System.out.println("6. Proses Pembayaran");
        System.out.println("7. Riwayat Transaksi");
        System.out.println("0. Keluar");
    }

    static int hitungTotalHarga(int harga, int porsi) {
        return harga * porsi;
    }

    static void tambahPesanan() {
        if (jumlahAntrean >= MAX_DATA) {
            System.out.println("Kapasitas antrean penuh!");
            return;
        }

        tampilkanDaftarMenu();

        System.out.print("Masukkan Nama Pelanggan: ");
        String nama = scanner.nextLine().trim();
        if (nama.isEmpty()) {
            System.out.println("Nama tidak boleh kosong!");
            return;
        }

        System.out.print("Pilih Nomor Menu (1-" + MAX_MENU + "): ");
        if (!scanner.hasNextInt()) {
            System.out.println("Input nomor menu tidak valid!");
            scanner.next();
            return;
        }
        int pilihMenu = scanner.nextInt();

        if (pilihMenu < 1 || pilihMenu > MAX_MENU) {
            System.out.println("Nomor menu tidak valid!");
            return;
        }

        System.out.print("Masukkan Jumlah Porsi: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Input porsi tidak valid!");
            scanner.next();
            return;
        }
        int porsi = scanner.nextInt();
        scanner.nextLine();

        if (porsi <= 0) {
            System.out.println("Jumlah porsi minimal 1!");
            return;
        }

        simpanPesanan(nama, pilihMenu - 1, porsi);
        System.out.println("Pesanan berhasil dicatat dengan ID: " + idPesanan[jumlahAntrean - 1]);
    }

    static void simpanPesanan(String nama, int menuIdx, int porsi) {
        counterOrder++;
        idPesanan[jumlahAntrean] = String.format("P%03d", counterOrder);
        namaPelanggan[jumlahAntrean] = nama;
        menuPesanan[jumlahAntrean] = namaMenu[menuIdx];
        jumlahPorsi[jumlahAntrean] = porsi;
        totalHarga[jumlahAntrean] = hitungTotalHarga(infoMenu[menuIdx][0], porsi);
        durasiMasak[jumlahAntrean] = infoMenu[menuIdx][1];
        jumlahAntrean++;
    }

    static void tampilkanDaftarMenu() {
        System.out.println("\n=== DAFTAR MENU RESTORAN ===");
        for (int i = 0; i < MAX_MENU; i++) {
            System.out.printf("%d. %-15s | Harga: Rp%-6d | Durasi: %2d menit%n",
                (i + 1), namaMenu[i], infoMenu[i][0], infoMenu[i][1]);
        }
    }

    static void tampilAntrean() {
        if (jumlahAntrean == 0) {
            System.out.println("Antrean pesanan masih kosong.");
            return;
        }

        System.out.println("\n=== DAFTAR ANTREAN MASAK ===");
        for (int i = 0; i < jumlahAntrean; i++) {
            System.out.println((i + 1) + ". [" + idPesanan[i] + "] " + namaPelanggan[i] +
                " - " + menuPesanan[i] + " (" + jumlahPorsi[i] + "x) | Durasi: " +
                durasiMasak[i] + " menit | Total: Rp" + totalHarga[i]);
        }
    }

    static void urutkanAntrean() {
        if (jumlahAntrean <= 1) {
            System.out.println("Data belum cukup diurutkan.");
            return;
        }

        bubbleSortAntrean();
        System.out.println("Antrean berhasil diurutkan berdasarkan durasi masak (tercepat ke terlama).");
    }

    static void bubbleSortAntrean() {
        // Bubble Sort ascending berdasarkan durasiMasak
        for (int i = 0; i < jumlahAntrean - 1; i++) {
            for (int j = 0; j < jumlahAntrean - i - 1; j++) {
                if (durasiMasak[j] > durasiMasak[j + 1]) {
                    // Tukar seluruh parallel array
                    int tempInt = durasiMasak[j];
                    durasiMasak[j] = durasiMasak[j + 1];
                    durasiMasak[j + 1] = tempInt;

                    String tempStr = idPesanan[j];
                    idPesanan[j] = idPesanan[j + 1];
                    idPesanan[j + 1] = tempStr;

                    tempStr = namaPelanggan[j];
                    namaPelanggan[j] = namaPelanggan[j + 1];
                    namaPelanggan[j + 1] = tempStr;

                    tempStr = menuPesanan[j];
                    menuPesanan[j] = menuPesanan[j + 1];
                    menuPesanan[j + 1] = tempStr;

                    tempInt = jumlahPorsi[j];
                    jumlahPorsi[j] = jumlahPorsi[j + 1];
                    jumlahPorsi[j + 1] = tempInt;

                    tempInt = totalHarga[j];
                    totalHarga[j] = totalHarga[j + 1];
                    totalHarga[j + 1] = tempInt;
                }
            }
        }
    }

    static void editPesanan() {
        if (jumlahAntrean == 0) {
            System.out.println("Antrean kosong. Tidak ada pesanan untuk diedit.");
            return;
        }

        tampilAntrean();
        System.out.print("Pilih nomor antrean yang ingin diedit (1-" + jumlahAntrean + "): ");
        if (!scanner.hasNextInt()) {
            System.out.println("Input nomor antrean tidak valid!");
            scanner.next();
            return;
        }
        int noUrut = scanner.nextInt();
        scanner.nextLine();

        if (noUrut < 1 || noUrut > jumlahAntrean) {
            System.out.println("Nomor antrean tidak valid!");
            return;
        }

        int idx = noUrut - 1;
        System.out.println("Mengedit pesanan ID: " + idPesanan[idx]);

        System.out.print("Masukkan Nama Baru (kosongkan jika tidak diubah): ");
        String namaBaru = scanner.nextLine().trim();
        if (!namaBaru.isEmpty()) {
            namaPelanggan[idx] = namaBaru;
        }

        tampilkanDaftarMenu();
        System.out.print("Pilih Menu Baru (1-" + MAX_MENU + ", 0 jika tidak diubah): ");
        if (!scanner.hasNextInt()) {
            System.out.println("Input menu tidak valid!");
            scanner.next();
            return;
        }
        int menuBaru = scanner.nextInt();

        int menuIdx = -1;
        if (menuBaru >= 1 && menuBaru <= MAX_MENU) {
            menuIdx = menuBaru - 1;
            menuPesanan[idx] = namaMenu[menuIdx];
            durasiMasak[idx] = infoMenu[menuIdx][1];
        } else if (menuBaru != 0) {
            System.out.println("Nomor menu tidak valid. Menu tidak diubah.");
        }

        System.out.print("Masukkan Jumlah Porsi Baru (0 jika tidak diubah): ");
        if (!scanner.hasNextInt()) {
            System.out.println("Input porsi tidak valid!");
            scanner.next();
            return;
        }
        int porsiBaru = scanner.nextInt();
        scanner.nextLine();

        if (porsiBaru > 0) {
            jumlahPorsi[idx] = porsiBaru;
        }

        // Hitung ulang harga jika menu atau porsi berubah
        int hargaPerItem = (menuIdx != -1) ? infoMenu[menuIdx][0] : cariHargaMenu(menuPesanan[idx]);
        totalHarga[idx] = hitungTotalHarga(hargaPerItem, jumlahPorsi[idx]);

        System.out.println("Pesanan " + idPesanan[idx] + " berhasil diperbarui!");
    }

    static int cariHargaMenu(String nama) {
        for (int i = 0; i < MAX_MENU; i++) {
            if (namaMenu[i].equalsIgnoreCase(nama)) {
                return infoMenu[i][0];
            }
        }
        return 0;
    }

    static void hapusPesanan() {
        if (jumlahAntrean == 0) {
            System.out.println("Antrean kosong. Tidak ada pesanan untuk dihapus.");
            return;
        }

        tampilAntrean();
        System.out.print("Pilih nomor antrean yang ingin dihapus (1-" + jumlahAntrean + "): ");
        if (!scanner.hasNextInt()) {
            System.out.println("Input nomor antrean tidak valid!");
            scanner.next();
            return;
        }
        int noUrut = scanner.nextInt();
        scanner.nextLine();

        if (noUrut < 1 || noUrut > jumlahAntrean) {
            System.out.println("Nomor antrean tidak valid!");
            return;
        }

        int idx = noUrut - 1;
        String idDihapus = idPesanan[idx];

        // Geser ke kiri untuk menghapus elemen
        for (int i = idx; i < jumlahAntrean - 1; i++) {
            idPesanan[i] = idPesanan[i + 1];
            namaPelanggan[i] = namaPelanggan[i + 1];
            menuPesanan[i] = menuPesanan[i + 1];
            jumlahPorsi[i] = jumlahPorsi[i + 1];
            totalHarga[i] = totalHarga[i + 1];
            durasiMasak[i] = durasiMasak[i + 1];
        }
        jumlahAntrean--;
        System.out.println("Pesanan " + idDihapus + " berhasil dihapus dari antrean.");
    }

    static void prosesPembayaran() {
        if (jumlahAntrean == 0) {
            System.out.println("Tidak ada antrean yang dapat dibayar.");
            return;
        }

        tampilAntrean();
        System.out.print("Pilih nomor antrean yang akan dibayar (1-" + jumlahAntrean + "): ");
        if (!scanner.hasNextInt()) {
            System.out.println("Input nomor antrean tidak valid!");
            scanner.next();
            return;
        }
        int noUrut = scanner.nextInt();

        if (noUrut < 1 || noUrut > jumlahAntrean) {
            System.out.println("Nomor antrean tidak valid!");
            return;
        }

        int idx = noUrut - 1;
        System.out.println("Total Tagihan: Rp" + totalHarga[idx]);
        System.out.print("Masukkan Nominal Uang Bayar: Rp");
        if (!scanner.hasNextInt()) {
            System.out.println("Input uang bayar tidak valid!");
            scanner.next();
            return;
        }
        int uangBayar = scanner.nextInt();
        scanner.nextLine();

        if (uangBayar < totalHarga[idx]) {
            System.out.println("Pembayaran gagal! Uang tidak mencukupi.");
            return;
        }

        // Catat ke riwayat transaksi lunas
        counterTrx++;
        idTransaksi[jumlahRiwayat] = String.format("TRX%03d", counterTrx);
        trxIdPesanan[jumlahRiwayat] = idPesanan[idx];
        trxPelanggan[jumlahRiwayat] = namaPelanggan[idx];
        trxMenu[jumlahRiwayat] = menuPesanan[idx];
        trxTotalBayar[jumlahRiwayat] = totalHarga[idx];
        jumlahRiwayat++;

        int kembalian = uangBayar - totalHarga[idx];
        System.out.println("Pembayaran Lunas! ID Transaksi: " + idTransaksi[jumlahRiwayat - 1] +
            " | Kembalian: Rp" + kembalian);

        // Hapus elemen dari antrean aktif (geser kiri)
        for (int i = idx; i < jumlahAntrean - 1; i++) {
            idPesanan[i] = idPesanan[i + 1];
            namaPelanggan[i] = namaPelanggan[i + 1];
            menuPesanan[i] = menuPesanan[i + 1];
            jumlahPorsi[i] = jumlahPorsi[i + 1];
            totalHarga[i] = totalHarga[i + 1];
            durasiMasak[i] = durasiMasak[i + 1];
        }
        jumlahAntrean--;
    }

    static void menuRiwayatTransaksi() {
        int subPilihan = 0;
        do {
            System.out.println("\n=== SUB-MENU RIWAYAT TRANSAKSI ===");
            System.out.println("1. Tampilkan Seluruh Riwayat");
            System.out.println("2. Cari Riwayat Transaksi (ID)");
            System.out.println("3. Kembali ke Menu Utama");
            System.out.print("Pilih sub-menu (1-3): ");
            if (!scanner.hasNextInt()) {
                System.out.println("Input harus angka!");
                scanner.next();
                continue;
            }
            subPilihan = scanner.nextInt();
            scanner.nextLine();

            switch (subPilihan) {
                case 1:
                    tampilSeluruhRiwayat();
                    break;
                case 2:
                    cariRiwayatTransaksi();
                    break;
                case 3:
                    System.out.println("Kembali ke menu utama...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (subPilihan != 3);
    }

    static void tampilSeluruhRiwayat() {
        if (jumlahRiwayat == 0) {
            System.out.println("Belum ada riwayat transaksi lunas.");
            return;
        }

        System.out.println("\n=== RINGKASAN RIWAYAT TRANSAKSI ===");
        for (int i = 0; i < jumlahRiwayat; i++) {
            System.out.println((i + 1) + ". [" + idTransaksi[i] + "] ID Pesanan: " +
                trxIdPesanan[i] + " | Pelanggan: " + trxPelanggan[i] +
                " | Menu: " + trxMenu[i] + " | Total: Rp" + trxTotalBayar[i]);
        }
    }

    static int linearSearchRiwayat(String cariID) {
        for (int i = 0; i < jumlahRiwayat; i++) {
            if (idTransaksi[i].equalsIgnoreCase(cariID)) {
                return i;
            }
        }
        return -1;
    }

    static void cariRiwayatTransaksi() {
        if (jumlahRiwayat == 0) {
            System.out.println("Belum ada riwayat transaksi.");
            return;
        }

        System.out.print("Masukkan ID Transaksi yang dicari: ");
        String cariID = scanner.nextLine().trim();

        int idxKetemu = linearSearchRiwayat(cariID);
        if (idxKetemu != -1) {
            System.out.println("\n=== DETAIL TRANSAKSI ===");
            System.out.println("ID Transaksi : " + idTransaksi[idxKetemu]);
            System.out.println("ID Pesanan   : " + trxIdPesanan[idxKetemu]);
            System.out.println("Pelanggan    : " + trxPelanggan[idxKetemu]);
            System.out.println("Menu         : " + trxMenu[idxKetemu]);
            System.out.println("Total Bayar  : Rp" + trxTotalBayar[idxKetemu]);
        } else {
            System.out.println("ID Transaksi tidak ditemukan.");
        }
    }

    // Runnable self-check saat boot
    static void jalankanSelfCheck() {
        if (hitungTotalHarga(18000, 2) != 36000) {
            throw new AssertionError("Self-check gagal: hitungTotalHarga");
        }

        // Test sorting
        simpanPesanan("TestA", 2, 1); // Ayam Bakar (25 menit)
        simpanPesanan("TestB", 4, 1); // Es Teh (3 menit)
        bubbleSortAntrean();
        if (durasiMasak[0] != 3 || durasiMasak[1] != 25) {
            throw new AssertionError("Self-check gagal: Bubble Sort tidak urut");
        }

        // Test searching
        idTransaksi[0] = "TRX001";
        jumlahRiwayat = 1;
        if (linearSearchRiwayat("TRX001") != 0 || linearSearchRiwayat("TRX999") != -1) {
            throw new AssertionError("Self-check gagal: Linear Search bermasalah");
        }

        // Reset state antrean & counter setelah test
        jumlahAntrean = 0;
        jumlahRiwayat = 0;
        counterOrder = 0;
        counterTrx = 0;
    }
}
