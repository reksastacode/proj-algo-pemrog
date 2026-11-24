import java.util.Scanner;

public class SistemAntreanRestoran {
    static final int MAX_ANTREAN = 100;
    static final int JUMLAH_MENU = 10;

    // Data master menu: Array 1D (nama menu) dan Array 2D (harga, durasi masak)
    static String[] namaMenu = {
        "Nasi Goreng", "Mie Goreng", "Ayam Geprek", "Nasi Ayam", "Soto Ayam",
        "Bakso", "Sate Ayam", "Nasi Pecel", "Es Teh", "Jus Jeruk"
    };
    static int[][] dataMenu = {
        {15000, 10},
        {12000, 8},
        {18000, 15},
        {17000, 12},
        {14000, 10},
        {13000, 8},
        {20000, 15},
        {12000, 7},
        {5000, 2},
        {8000, 3}
    };

    // Array 1D data pesanan
    static String[] idPesanan = new String[MAX_ANTREAN];
    static String[] namaPelanggan = new String[MAX_ANTREAN];
    static String[] rincianPesanan = new String[MAX_ANTREAN];
    static int[] durasiPesanan = new int[MAX_ANTREAN];
    static int[] totalPesanan = new int[MAX_ANTREAN];

    // Array 1D data transaksi
    static String[] idTransaksi = new String[MAX_ANTREAN];
    static String[] idPesananTransaksi = new String[MAX_ANTREAN];
    static String[] namaPelangganTransaksi = new String[MAX_ANTREAN];
    static String[] rincianTransaksi = new String[MAX_ANTREAN];
    static int[] totalTransaksi = new int[MAX_ANTREAN];

    // Counter dan penanda ukuran
    static int jumlahAntrean = 0;
    static int nomorPesanan = 0;
    static int jumlahTransaksi = 0;
    static int nomorTransaksi = 0;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        jalankanSelfCheck();

        int pilihan;
        do {
            tampilkanPilihan();
            pilihan = bacaInt("Pilih menu (1-9): ");

            switch (pilihan) {
                case 1:
                    if (jumlahAntrean < MAX_ANTREAN) {
                        tambahPesanan();
                        jumlahAntrean++;
                        nomorPesanan++;
                    } else {
                        System.out.println("Antrean sudah penuh");
                    }
                    break;
                case 2:
                    tampilkanAntrean();
                    break;
                case 3:
                    urutkanAntrean();
                    break;
                case 4:
                    editPesanan();
                    break;
                case 5:
                    jumlahAntrean = hapusPesanan();
                    break;
                case 6:
                    if (prosesPembayaran() == 1) {
                        jumlahAntrean--;
                        jumlahTransaksi++;
                        nomorTransaksi++;
                    }
                    break;
                case 7:
                    tampilkanSeluruhRiwayat();
                    break;
                case 8:
                    cariRiwayatTransaksi();
                    break;
                case 9:
                    System.out.println("Terima kasih telah menggunakan sistem");
                    break;
                default:
                    System.out.println("Pilihan tidak valid");
            }
        } while (pilihan != 9);
    }

    static void tampilkanPilihan() {
        System.out.println("\n=== SISTEM ANTREAN RESTORAN ===");
        System.out.println("1. Tambah Pesanan");
        System.out.println("2. Tampilkan Antrean");
        System.out.println("3. Urutkan Antrean");
        System.out.println("4. Edit Pesanan");
        System.out.println("5. Hapus Pesanan");
        System.out.println("6. Proses Pembayaran");
        System.out.println("7. Riwayat Transaksi");
        System.out.println("8. Cari Transaksi");
        System.out.println("9. Keluar");
    }

    static void tampilkanMenu() {
        System.out.println("\n=== DAFTAR MENU ===");
        for (int i = 0; i < JUMLAH_MENU; i++) {
            System.out.printf("%2d. %-12s | Harga: Rp%-6d | Durasi: %2d menit%n",
                (i + 1), namaMenu[i], dataMenu[i][0], dataMenu[i][1]);
        }
    }

    static void tambahPesanan() {
        String nama = bacaString("Masukkan nama pelanggan: ");
        while (nama.isEmpty()) {
            System.out.println("Nama pelanggan tidak boleh kosong");
            nama = bacaString("Masukkan nama pelanggan: ");
        }

        int totalInput = 0;
        int durasiTotal = 0;
        StringBuilder rincianInput = new StringBuilder();
        String tambahLagi = "Ya";

        while (tambahLagi.equalsIgnoreCase("Ya")) {
            tampilkanMenu();
            int nomorMenuInput = bacaNomorMenu();
            int jumlahInput = bacaIntMin("Masukkan jumlah pesanan: ", 1);

            int idx = nomorMenuInput - 1;
            totalInput = totalInput + dataMenu[idx][0] * jumlahInput;
            durasiTotal = durasiTotal + dataMenu[idx][1] * jumlahInput;
            rincianInput.append(namaMenu[idx]).append(" x ").append(jumlahInput).append(", ");

            tambahLagi = bacaYaTidak("Tambah menu lagi? (Ya/Tidak): ");
        }

        int i = jumlahAntrean;
        idPesanan[i] = "P" + (nomorPesanan + 1);
        namaPelanggan[i] = nama;
        rincianPesanan[i] = rincianInput.substring(0, rincianInput.length() - 2);
        durasiPesanan[i] = durasiTotal;
        totalPesanan[i] = totalInput;

        System.out.println("Pesanan berhasil ditambahkan dengan ID: " + idPesanan[i]);
    }

    static void tampilkanAntrean() {
        if (jumlahAntrean == 0) {
            System.out.println("Antrean masih kosong");
            return;
        }

        System.out.println("\n=== DAFTAR ANTREAN ===");
        for (int i = 0; i < jumlahAntrean; i++) {
            System.out.println("ANTREAN PESANAN KE-" + (i + 1));
            System.out.println("=============================");
            System.out.println("ID Pesanan: " + idPesanan[i]);
            System.out.println("Nama Pelanggan: " + namaPelanggan[i]);
            System.out.println("Rincian Pesanan: " + rincianPesanan[i]);
            System.out.println("Durasi: " + durasiPesanan[i] + " menit");
            System.out.println("Total Harga: Rp" + totalPesanan[i]);
            System.out.println("-----------------------------");
        }
    }

    static void urutkanAntrean() {
        if (jumlahAntrean > 1) {
            bubbleSortAntrean();
            System.out.println("Antrean berhasil diurutkan");
        } else {
            System.out.println("Antrean tidak perlu diurutkan");
        }
    }

    static void bubbleSortAntrean() {
        // Bubble Sort ascending berdasarkan durasiPesanan
        for (int i = 0; i < jumlahAntrean - 1; i++) {
            for (int j = 0; j < jumlahAntrean - i - 1; j++) {
                if (durasiPesanan[j] > durasiPesanan[j + 1]) {
                    int tempInt = durasiPesanan[j];
                    durasiPesanan[j] = durasiPesanan[j + 1];
                    durasiPesanan[j + 1] = tempInt;

                    tempInt = totalPesanan[j];
                    totalPesanan[j] = totalPesanan[j + 1];
                    totalPesanan[j + 1] = tempInt;

                    String tempString = idPesanan[j];
                    idPesanan[j] = idPesanan[j + 1];
                    idPesanan[j + 1] = tempString;

                    tempString = namaPelanggan[j];
                    namaPelanggan[j] = namaPelanggan[j + 1];
                    namaPelanggan[j + 1] = tempString;

                    tempString = rincianPesanan[j];
                    rincianPesanan[j] = rincianPesanan[j + 1];
                    rincianPesanan[j + 1] = tempString;
                }
            }
        }
    }

    static void editPesanan() {
        if (jumlahAntrean == 0) {
            System.out.println("Antrean kosong");
            return;
        }

        tampilkanAntrean();
        String idCari = bacaString("Masukkan ID pesanan yang ingin diedit: ");
        int i = cariIndexPesanan(idCari);

        if (i == -1) {
            System.out.println("Pesanan tidak ditemukan");
            return;
        }

        String namaBaru = bacaString("Masukkan nama pelanggan: ");
        while (namaBaru.isEmpty()) {
            System.out.println("Nama pelanggan tidak boleh kosong");
            namaBaru = bacaString("Masukkan nama pelanggan: ");
        }

        int totalInput = 0;
        int durasiTotal = 0;
        StringBuilder rincianInput = new StringBuilder();
        String tambahLagi = "Ya";

        while (tambahLagi.equalsIgnoreCase("Ya")) {
            tampilkanMenu();
            int nomorMenuInput = bacaNomorMenu();
            int jumlahInput = bacaIntMin("Masukkan jumlah pesanan: ", 1);

            int idx = nomorMenuInput - 1;
            totalInput = totalInput + dataMenu[idx][0] * jumlahInput;
            durasiTotal = durasiTotal + dataMenu[idx][1] * jumlahInput;
            rincianInput.append(namaMenu[idx]).append(" x ").append(jumlahInput).append(", ");

            tambahLagi = bacaYaTidak("Tambah menu lagi? (Ya/Tidak): ");
        }

        namaPelanggan[i] = namaBaru;
        rincianPesanan[i] = rincianInput.substring(0, rincianInput.length() - 2);
        durasiPesanan[i] = durasiTotal;
        totalPesanan[i] = totalInput;

        System.out.println("Pesanan berhasil diedit");
    }

    static int hapusPesanan() {
        if (jumlahAntrean == 0) {
            System.out.println("Antrean kosong");
            return jumlahAntrean;
        }

        tampilkanAntrean();
        String idCari = bacaString("Masukkan ID pesanan yang ingin dihapus: ");
        int i = cariIndexPesanan(idCari);

        if (i == -1) {
            System.out.println("Pesanan tidak ditemukan");
            return jumlahAntrean;
        }

        for (int k = i; k < jumlahAntrean - 1; k++) {
            idPesanan[k] = idPesanan[k + 1];
            namaPelanggan[k] = namaPelanggan[k + 1];
            rincianPesanan[k] = rincianPesanan[k + 1];
            durasiPesanan[k] = durasiPesanan[k + 1];
            totalPesanan[k] = totalPesanan[k + 1];
        }
        jumlahAntrean--;
        System.out.println("Pesanan berhasil dihapus");

        return jumlahAntrean;
    }

    static int prosesPembayaran() {
        if (jumlahAntrean == 0) {
            System.out.println("Antrean kosong");
            return 0;
        }

        if (jumlahTransaksi >= MAX_ANTREAN) {
            System.out.println("Riwayat transaksi sudah penuh");
            return 0;
        }

        tampilkanAntrean();
        String idCari = bacaString("Masukkan ID pesanan yang ingin dibayar: ");
        int i = cariIndexPesanan(idCari);

        if (i == -1) {
            System.out.println("Pesanan tidak ditemukan");
            return 0;
        }

        String konfirmasi = bacaYaTidak("Konfirmasi pembayaran? (Ya/Tidak): ");
        if (!konfirmasi.equalsIgnoreCase("Ya")) {
            System.out.println("Pembayaran dibatalkan");
            return 0;
        }

        idTransaksi[jumlahTransaksi] = "T" + (nomorTransaksi + 1);
        idPesananTransaksi[jumlahTransaksi] = idPesanan[i];
        namaPelangganTransaksi[jumlahTransaksi] = namaPelanggan[i];
        rincianTransaksi[jumlahTransaksi] = rincianPesanan[i];
        totalTransaksi[jumlahTransaksi] = totalPesanan[i];

        System.out.println("Pembayaran berhasil! ID Transaksi: " + idTransaksi[jumlahTransaksi]);

        for (int k = i; k < jumlahAntrean - 1; k++) {
            idPesanan[k] = idPesanan[k + 1];
            namaPelanggan[k] = namaPelanggan[k + 1];
            rincianPesanan[k] = rincianPesanan[k + 1];
            durasiPesanan[k] = durasiPesanan[k + 1];
            totalPesanan[k] = totalPesanan[k + 1];
        }

        return 1;
    }

    static void tampilkanSeluruhRiwayat() {
        if (jumlahTransaksi == 0) {
            System.out.println("Belum ada riwayat transaksi");
            return;
        }

        for (int i = 0; i < jumlahTransaksi; i++) {
            System.out.println("==============================");
            System.out.println("TRANSAKSI KE-" + (i + 1));
            System.out.println("ID Transaksi: " + idTransaksi[i]);
            System.out.println("ID Pesanan: " + idPesananTransaksi[i]);
            System.out.println("Nama Pelanggan: " + namaPelangganTransaksi[i]);
            System.out.println("Rincian Transaksi: " + rincianTransaksi[i]);
            System.out.println("Total Pembayaran: Rp" + totalTransaksi[i]);
            System.out.println("==============================");
        }
    }

    static void cariRiwayatTransaksi() {
        if (jumlahTransaksi == 0) {
            System.out.println("Belum ada riwayat transaksi");
            return;
        }

        String idCari = bacaString("Masukkan ID Transaksi: ");
        int i = cariIndexTransaksi(idCari);

        if (i == -1) {
            System.out.println("Transaksi tidak ditemukan");
            return;
        }

        System.out.println("Transaksi ditemukan");
        System.out.println("==============================");
        System.out.println("ID Transaksi: " + idTransaksi[i]);
        System.out.println("ID Pesanan: " + idPesananTransaksi[i]);
        System.out.println("Nama Pelanggan: " + namaPelangganTransaksi[i]);
        System.out.println("Rincian Transaksi: " + rincianTransaksi[i]);
        System.out.println("Total Pembayaran: Rp" + totalTransaksi[i]);
        System.out.println("==============================");
    }

    // Linear Search data pesanan berdasarkan ID Pesanan
    static int cariIndexPesanan(String idCari) {
        int i = 0;
        while (i < jumlahAntrean) {
            if (idPesanan[i].equalsIgnoreCase(idCari)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    // Linear Search data transaksi berdasarkan ID Transaksi
    static int cariIndexTransaksi(String idCari) {
        for (int i = 0; i < jumlahTransaksi; i++) {
            if (idTransaksi[i].equalsIgnoreCase(idCari)) {
                return i;
            }
        }
        return -1;
    }

    static int bacaInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Input harus angka!");
            scanner.next();
            System.out.print(prompt);
        }
        int nilai = scanner.nextInt();
        scanner.nextLine();
        return nilai;
    }

    static int bacaIntMin(String prompt, int minimal) {
        int nilai = bacaInt(prompt);
        while (nilai < minimal) {
            System.out.println("Jumlah harus lebih dari 0");
            nilai = bacaInt(prompt);
        }
        return nilai;
    }

    static int bacaNomorMenu() {
        int nomor = bacaInt("Masukkan nomor menu: ");
        while (nomor < 1 || nomor > JUMLAH_MENU) {
            System.out.println("Nomor menu tidak valid");
            nomor = bacaInt("Masukkan nomor menu: ");
        }
        return nomor;
    }

    static String bacaString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    static String bacaYaTidak(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        while (!input.equalsIgnoreCase("Ya") && !input.equalsIgnoreCase("Tidak")) {
            System.out.println("Input hanya Ya atau Tidak");
            System.out.print(prompt);
            input = scanner.nextLine().trim();
        }
        return input;
    }

    // Self-check Bubble Sort dan Linear Search saat program dijalankan
    static void jalankanSelfCheck() {
        idPesanan[0] = "P1";
        namaPelanggan[0] = "TestA";
        rincianPesanan[0] = "Nasi Goreng x 1";
        durasiPesanan[0] = 10;
        totalPesanan[0] = 15000;

        idPesanan[1] = "P2";
        namaPelanggan[1] = "TestB";
        rincianPesanan[1] = "Es Teh x 1";
        durasiPesanan[1] = 2;
        totalPesanan[1] = 5000;

        jumlahAntrean = 2;
        bubbleSortAntrean();
        if (durasiPesanan[0] != 2 || durasiPesanan[1] != 10) {
            throw new AssertionError("Self-check gagal: Bubble Sort tidak urut");
        }
        if (cariIndexPesanan("P1") != 1) {
            throw new AssertionError("Self-check gagal: Linear Search pesanan bermasalah");
        }

        idTransaksi[0] = "T1";
        jumlahTransaksi = 1;
        if (cariIndexTransaksi("T1") != 0 || cariIndexTransaksi("T999") != -1) {
            throw new AssertionError("Self-check gagal: Linear Search transaksi bermasalah");
        }

        jumlahAntrean = 0;
        jumlahTransaksi = 0;
    }
}
