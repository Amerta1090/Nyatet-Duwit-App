# NyatetDuwit — Product Strategy & Feature System

> **Tagline**: Nyatet duit, hidup tenang.
> **Platform**: Android (Offline-First)
> **Target**: Gen Z & pekerja muda Indonesia
> **Positioning**: Personal finance app yang cepat, private, ringan, dan terasa sangat niat.

---

## A. PRODUCT STRATEGY SUMMARY

### Core Philosophy

NyatetDuwit berdiri di atas satu prinsip: **friction kills habit**. Aplikasi finance paling canggih di dunia tidak ada gunanya kalau user malas buka. Setiap keputusan produk harus melewati filter ini:

> "Apakah ini membuat user lebih mungkin mencatat hari ini?"

Jika jawabannya tidak atau ragu-ragu → jangan dibuat.

### Competitive Landscape & Benchmarking

| App | Yang Dipelajari | Yang Dibuang |
|-----|----------------|--------------|
| **YNAB** | Zero-based budgeting philosophy, rule system, "give every dollar a job" mindset | Terlalu rigid, learning curve curam, subscription mahal |
| **Money Lover** | Multi-account, budget categories, report visual | Bloated, banyak iklan, UI terasa 2018 |
| **Wallet by BudgetBakers** | Bank sync, recurring transactions, multi-currency | Terlalu kompleks, sync sering error, berat |
| **Monarch Money** | Clean UI, net worth tracking, shared accounts | US-centric, subscription-only, tidak relevan untuk Indonesia |
| **Copilot Money** | AI categorization, beautiful design, iOS-native feel | iOS only, mahal, AI overhyped untuk personal finance |
| **Spendee** | Shared wallets, visual categories, clean onboarding | Terlalu simpel, kurang depth, fitur premium paywall semua |
| **Google Sheets** | Fleksibel, gratis, customizable, power user friendly | Manual, tidak mobile-friendly, tidak ada UX |

### What NyatetDuwit Ambil dari Semua Itu

1. **YNAB**: Budgeting philosophy yang sederhana (bukan full zero-based, tapi "awareness-based")
2. **Money Lover**: Multi-account system yang jelas
3. **Copilot**: Design quality dan attention to detail
4. **Spendee**: Simplicity di input flow
5. **Google Sheets**: Fleksibilitas tanpa kompleksitas

### Differentiator NyatetDuwit

1. **Offline-first yang beneran** — bukan gimmick, tapi arsitektur utama
2. **Input <3 detik** — ini bukan target, ini requirement
3. **Context-aware** — paham kebiasaan user, bukan cuma nampung data
4. **Anti-boncos system** — bukan budget yang bikin stress, tapi warning yang timely
5. **Feels premium without being heavy** — Material 3, smooth animations, tapi APK <30MB

---

## B. FEATURE HIERARCHY

### LEVEL 1: FOUNDATION (WAJIB ADA)

Fitur minimum supaya app layak dipakai. Tanpa ini, app bukan apa-apa.

---

#### F01 — Quick Transaction Input
- **Masalah**: User malas buka app kalau input lama
- **Kenapa penting**: Ini adalah fitur #1 yang menentukan app dipakai atau tidak
- **User value**: Catat transaksi dalam <3 detik
- **UX expectation**: 
  - Buka app → langsung di screen input atau 1 tap ke input
  - Numpad custom (bukan keyboard system) dengan preset nominal (10K, 20K, 50K, 100K)
  - Kategori terakhir muncul sebagai suggestion
  - Swipe untuk switch income/expense
  - Haptic feedback di setiap tap
- **Retention**: ★★★★★ (tanpa ini, user drop di hari 1)
- **Trust**: ★★★★☆ (cepat = terasa reliable)
- **Daily usage**: ★★★★★ (ini yang bikin daily)
- **Kompleksitas**: Sedang
- **Priority**: P0

---

#### F02 — Basic Transaction Types
- **Masalah**: User butuh catat berbagai jenis transaksi
- **Kenapa penting**: Fondasi data model
- **User value**: Bisa catat pemasukan, pengeluaran, dan transfer
- **UX expectation**:
  - 3 tab jelas: Income / Expense / Transfer
  - Transfer: pilih akun asal & tujuan, nominal otomatis balance
  - Form minimalis: nominal → kategori → akun → catatan (opsional)
  - Undo setelah save (3 detik snackbar)
- **Retention**: ★★★★★
- **Trust**: ★★★★☆
- **Daily usage**: ★★★★★
- **Kompleksitas**: Rendah
- **Priority**: P0

---

#### F03 — Account System (Multi-Akun)
- **Masalah**: User punya cash, bank, e-wallet — semua perlu dilacak
- **Kenapa penting**: Tanpa ini, user tidak tahu total kekayaan mereka
- **User value**: Lihat saldo real-time per akun dan total net worth
- **UX expectation**:
  - Setup awal: wizard tambah akun (cash, bank, e-wallet)
  - Icon per tipe akun (💵 Cash, 🏦 Bank, 📱 E-Wallet)
  - Saldo ter-update otomatis setiap transaksi
  - Bisa hide/unhide akun (privasi)
  - Reorder akun dengan drag
- **Retention**: ★★★★☆
- **Trust**: ★★★★★ (user percaya app kalau saldo akurat)
- **Daily usage**: ★★★★☆
- **Kompleksitas**: Rendah
- **Priority**: P0

---

#### F04 — Category System
- **Masalah**: Tanpa kategori, data tidak bisa dianalisa
- **Kenapa penting**: Fondasi untuk semua insight dan budgeting
- **User value**: Organisasi transaksi yang meaningful
- **UX expectation**:
  - Default categories yang relevan untuk Indonesia (Makan, Transport, Belanja, Hiburan, Tagihan, dll)
  - Custom icon & color per kategori
  - Bisa tambah/edit/hapus
  - Kategori terakhir muncul di quick input
  - Subkategori opsional (jangan dipaksa)
- **Retention**: ★★★★☆
- **Trust**: ★★★☆☆
- **Daily usage**: ★★★★★
- **Kompleksitas**: Rendah
- **Priority**: P0

---

#### F05 — Transaction List & History
- **Masalah**: User perlu lihat kembali transaksi sebelumnya
- **Kenapa penting**: Tanpa history, app tidak ada gunanya
- **User value**: Lihat semua transaksi, edit, hapus
- **UX expectation**:
  - Grouped by date (Today, Yesterday, Last Week, dll)
  - Swipe left = edit, swipe right = delete (dengan confirm)
  - Tap = detail view
  - Pull to refresh (walaupun offline, tetap kasih feedback)
  - Infinite scroll yang smooth
  - Empty state yang encouraging (bukan "no data" yang depressing)
- **Retention**: ★★★★☆
- **Trust**: ★★★★★
- **Daily usage**: ★★★☆☆
- **Kompleksitas**: Rendah
- **Priority**: P0

---

#### F06 — Dashboard / Home Screen
- **Masalah**: User butuh overview keuangan sekilas
- **Kenapa penting**: First screen yang user lihat setiap hari
- **User value**: Lihat ringkasan keuangan dalam 5 detik
- **UX expectation**:
  - Total balance (bisa toggle show/hide)
  - Income vs Expense bulan ini
  - Top 3 kategori pengeluaran bulan ini
  - Quick input button (floating, selalu visible)
  - Recent transactions (5 terakhir)
  - Jangan overload — whitespace is luxury
- **Retention**: ★★★★★
- **Trust**: ★★★★☆
- **Daily usage**: ★★★★★
- **Kompleksitas**: Rendah
- **Priority**: P0

---

#### F07 — Local Database (Room/SQLite)
- **Masalah**: App harus jalan tanpa internet
- **Kenapa penting**: Ini adalah constraint utama produk
- **User value**: App selalu usable, data tidak hilang
- **UX expectation**:
  - Zero loading state saat offline (karena memang offline)
  - Data tersimpan lokal, instant access
  - No "checking connection..." spinner yang annoying
- **Retention**: ★★★★★
- **Trust**: ★★★★★ (ini fondasi trust)
- **Daily usage**: ★★★★★
- **Kompleksitas**: Sedang
- **Priority**: P0

---

#### F08 — PIN / Biometric Lock
- **Masalah**: Data keuangan sensitif, user butuh rasa aman
- **Kenapa penting**: Tanpa ini, user tidak percaya
- **User value**: App terkunci, data aman dari orang lain
- **UX expectation**:
  - Biometric (fingerprint/face) sebagai default
  - PIN fallback kalau biometric gagal
  - Lock after X menit tidak aktif (configurable)
  - Lock on app background (Android lifecycle aware)
  - Setup di onboarding, bukan nanti
- **Retention**: ★★★☆☆
- **Trust**: ★★★★★ (non-negotiable untuk finance app)
- **Daily usage**: ★★★★☆ (biometric harus <1 detik, kalau lama user kesal)
- **Kompleksitas**: Rendah
- **Priority**: P0

---

### LEVEL 2: CORE PERSONAL FINANCE

Fitur inti yang membuat user benar-benar terbantu sehari-hari.

---

#### F09 — Monthly Budget
- **Masalah**: User tidak tahu apakah pengeluaran mereka wajar
- **Kenapa penting**: Budget = kesadaran finansial
- **User value**: Set limit pengeluaran bulanan, lihat progress
- **UX expectation**:
  - Set budget total atau per kategori
  - Progress bar dengan color coding (hijau → kuning → merah)
  - Warning saat 80% dan 100% terpakai
  - Bukan yang rigid seperti YNAB — lebih "awareness" daripada "enforcement"
  - Bisa pause budget (kalau ada bulan spesial)
- **Retention**: ★★★★★
- **Trust**: ★★★★☆
- **Daily usage**: ★★★★☆
- **Kompleksitas**: Rendah
- **Priority**: P0

---

#### F10 — Recurring Transactions
- **Masalah**: Tagihan bulanan (listrik, internet, kos, subscription) selalu sama
- **Kenapa penting**: Mengurangi input manual yang membosankan
- **User value**: Auto-catat transaksi berulang
- **UX expectation**:
  - Setup: pilih transaksi → set recurring (daily/weekly/monthly/yearly)
  - Notifikasi lokal saat recurring transaction due
  - Bisa skip atau edit satu instance tanpa affect yang lain
  - Preview upcoming recurring transactions
- **Retention**: ★★★★☆
- **Trust**: ★★★★☆
- **Daily usage**: ★★★☆☆
- **Kompleksitas**: Sedang
- **Priority**: P0

---

#### F11 — Transaction Templates
- **Masalah**: Transaksi yang sama diinput berulang (kopi pagi, parkir, makan siang)
- **Kenapa penting**: Mengurangi friction untuk transaksi rutin
- **User value**: 1 tap untuk transaksi yang biasa dilakukan
- **UX expectation**:
  - Long press transaksi → "Save as Template"
  - Template muncul di quick input sebagai chip/suggestion
  - Bisa edit nominal sebelum save (karena harga bisa berubah)
  - Template favorit di-pin di atas
- **Retention**: ★★★★☆
- **Trust**: ★★★☆☆
- **Daily usage**: ★★★★★ (ini yang bikin <3 detik beneran)
- **Kompleksitas**: Rendah
- **Priority**: P0

---

#### F12 — Split Transaction
- **Masalah**: Satu transaksi bisa punya multiple kategori (beli di minimarket: makanan + kebutuhan rumah)
- **Kenapa penting**: Akurasi pencatatan
- **User value**: Satu transaksi, multiple kategori
- **UX expectation**:
  - Di form transaksi → tap "Split"
  - Tambah baris: kategori + nominal
  - Total harus match dengan nominal transaksi
  - Visual progress: "Rp 50.000 / Rp 150.000 allocated"
  - Jangan terlalu kompleks — max 5 splits sudah cukup
- **Retention**: ★★★☆☆
- **Trust**: ★★★★☆
- **Daily usage**: ★★☆☆☆ (tidak sering, tapi crucial saat perlu)
- **Kompleksitas**: Sedang
- **Priority**: P1

---

#### F13 — Tags System
- **Masalah**: Kategori tidak cukup granular (misal: "Makan" terlalu luas)
- **Kenapa penting**: Fleksibilitas organisasi tanpa menambah kompleksitas kategori
- **User value**: Label tambahan untuk filter dan grouping
- **UX expectation**:
  - Tags opsional di form transaksi
  - Auto-suggest dari tags yang pernah dipakai
  - Bisa filter by tag di transaction list
  - Hashtag-style input (#liburan, #tanggaltua, #gajian)
- **Retention**: ★★★☆☆
- **Trust**: ★★★☆☆
- **Daily usage**: ★★☆☆☆
- **Kompleksitas**: Rendah
- **Priority**: P1

---

#### F14 — Notes & Receipt Attachment
- **Masalah**: User butuh konteks lebih dari sekadar nominal dan kategori
- **Kenapa penting**: Untuk transaksi yang perlu bukti atau catatan khusus
- **User value**: Tambah catatan dan foto struk
- **UX expectation**:
  - Notes: text field di form transaksi
  - Attachment: foto struk (opsional, jangan dipaksa)
  - Compress image otomatis (max 500KB per attachment)
  - Preview thumbnail di transaction list
  - Jangan jadikan ini mandatory — friction killer
- **Retention**: ★★☆☆☆
- **Trust**: ★★★☆☆
- **Daily usage**: ★★☆☆☆
- **Kompleksitas**: Sedang
- **Priority**: P1

---

#### F15 — Search & Filter
- **Masalah**: User butuh cari transaksi spesifik di antara ratusan entry
- **Kenapa penting**: Produktivitas
- **User value**: Cari transaksi dengan cepat
- **UX expectation**:
  - Global search: by nominal, kategori, notes, tags
  - Filter: date range, kategori, akun, tipe transaksi, tags
  - Search results highlight match
  - Recent searches
  - Filter combination (date + kategori + akun)
- **Retention**: ★★★☆☆
- **Trust**: ★★★★☆
- **Daily usage**: ★★★☆☆
- **Kompleksitas**: Sedang
- **Priority**: P0

---

#### F16 — Monthly Summary / Review
- **Masalah**: User butuh review keuangan secara berkala
- **Kenapa penting**: Ini yang bikin user "sadar" pola keuangan mereka
- **User value**: Lihat ringkasan keuangan bulanan
- **UX expectation**:
  - Total income, expense, net savings
  - Top 5 kategori pengeluaran (dengan chart)
  - Comparison vs bulan sebelumnya (↑↓ %)
  - Biggest transaction of the month
  - Spending trend line
  - Shareable summary (image, untuk social proof)
- **Retention**: ★★★★★
- **Trust**: ★★★★☆
- **Daily usage**: ★☆☆☆☆ (monthly, tapi high impact)
- **Kompleksitas**: Sedang
- **Priority**: P0

---

### LEVEL 3: RETENTION & HABIT SYSTEM

Fitur yang membuat user konsisten mencatat dan kembali menggunakan aplikasi.

---

#### F17 — Smart Reminder
- **Masalah**: User lupa mencatat, terutama di awal-awal
- **Kenapa penting**: Habit formation butuh trigger
- **User value**: Diingatkan untuk mencatat pada waktu yang tepat
- **UX expectation**:
  - Notifikasi lokal (bukan push, karena offline-first)
  - Smart timing: based on user's usual input time
  - Adaptive: kalau user sudah rajin, reminder berkurang
  - Gentle tone, bukan nagging ("Belum nyatet hari ini nih 👀" bukan "YOU MUST RECORD NOW")
  - Bisa snooze atau dismiss
  - Jangan lebih dari 2 reminder per hari
- **Retention**: ★★★★★ (ini yang bikin user comeback)
- **Trust**: ★★★☆☆
- **Daily usage**: ★★★★★
- **Kompleksitas**: Rendah
- **Priority**: P0

---

#### F18 — Weekly Check-in
- **Masalah**: User tidak punya momen refleksi keuangan
- **Kenapa penting**: Weekly review lebih actionable daripada monthly
- **User value**: Ringkasan mingguan yang cepat dibaca
- **UX expectation**:
  - Muncul setiap Senin pagi (atau hari pertama user aktif)
  - "Minggu ini kamu spend Rp X, turun/naik Y% dari minggu lalu"
  - Top 3 kategori
  - "Kamu sudah catat Z transaksi — bagus!" atau "Kayaknya minggu ini lupa-lupa nih"
  - Swipe away kalau tidak mau baca
  - Format: card yang bisa di-scroll, bukan full screen
- **Retention**: ★★★★☆
- **Trust**: ★★★★☆
- **Daily usage**: ★☆☆☆☆ (weekly)
- **Kompleksitas**: Rendah
- **Priority**: P1

---

#### F19 — Streak System (REDESIGNED)
- **Masalah**: Streak bisa jadi motivator, tapi juga bikin user quit kalau putus
- **Kenapa penting**: Gamifikasi yang benar tidak bikin stress
- **User value**: Lihat konsistensi tanpa pressure berlebihan
- **UX expectation**:
  - Streak = hari berturut-turut buka app DAN catat transaksi
  - TAPI: ada "streak freeze" (1x per bulan bisa skip tanpa putus)
  - Tidak ada guilt trip kalau streak putus
  - Milestone celebration (7 hari, 30 hari, 100 hari) — subtle, bukan confetti berlebihan
  - Streak ditampilkan di profile, bukan di home (jangan jadi pressure)
  - **OPINI**: Streak itu double-edged sword. Kalau salah implementasi, user malah uninstall karena merasa gagal. Harus sangat hati-hati.
- **Retention**: ★★★★☆ (kalau done right), ★☆☆☆☆ (kalau done wrong)
- **Trust**: ★★★☆☆
- **Daily usage**: ★★★★☆
- **Kompleksitas**: Rendah
- **Priority**: P1

---

#### F20 — Anti Drop-off System
- **Masalah**: User berhenti pakai app setelah 1-2 minggu
- **Kenapa penting**: Retention adalah masalah #1 finance app
- **User value**: App yang "ngajak balik" tanpa annoying
- **UX expectation**:
  - Setelah 3 hari tidak aktif: gentle reminder ("Kangen nih, belum nyatet 3 hari")
  - Setelah 7 hari: "Mau lanjutin streak? Kamu udah sampe X hari lho"
  - Setelah 14 hari: "Gapapa kok break, mau mulai lagi? Data kamu masih aman"
  - Setelah 30 hari: "Welcome back! Ini ringkasan terakhir kamu..."
  - Setiap pesan harus empathetic, bukan judgmental
  - **Jangan**: "You're falling behind!" atau "Your finances are at risk!"
- **Retention**: ★★★★★ (ini yang bikin app tidak di-uninstall)
- **Trust**: ★★★★☆
- **Daily usage**: ★★★★☆
- **Kompleksitas**: Rendah
- **Priority**: P1

---

#### F21 — Progress Feedback
- **Masalah**: User tidak merasa progress dari mencatat
- **Kenapa penting**: User butuh merasa "ini berguna"
- **User value**: Lihat dampak dari kebiasaan mencatat
- **UX expectation**:
  - "Kamu sudah catat X transaksi bulan ini"
  - "Total yang tercatat: Rp X"
  - "Insight: 40% pengeluaran kamu untuk makan. Mau coba kurangi?"
  - Progress yang ditampilkan harus actionable, bukan vanity metric
- **Retention**: ★★★★☆
- **Trust**: ★★★★☆
- **Daily usage**: ★★★☆☆
- **Kompleksitas**: Rendah
- **Priority**: P1

---

### LEVEL 4: PREMIUM / WORLD-CLASS EXPERIENCE

Fitur yang membuat app terasa mahal, cerdas, polished, dan sulit ditinggalkan.

---

#### F22 — Anti-Boncos System
- **Masalah**: User kehabisan uang di akhir bulan tanpa sadar
- **Kenapa penting**: Ini adalah "killer feature" yang bikin app beda
- **User value**: Warning dini sebelum uang habis
- **UX expectation**:
  - Kalkulasi: (sisa hari di bulan) vs (sisa budget)
  - "Kamu punya Rp 500K untuk 10 hari sisa = Rp 50K/hari. Aman!" atau "Waduh, cuma Rp 20K/hari. Hmm."
  - Daily spending allowance yang ter-update real-time
  - Visual: gauge meter atau progress bar yang intuitif
  - Notifikasi saat daily allowance berubah drastis
  - **Ini bukan budgeting rigid** — ini "awareness companion"
- **Retention**: ★★★★★
- **Trust**: ★★★★★
- **Daily usage**: ★★★★★
- **Kompleksitas**: Sedang
- **Priority**: P1

---

#### F23 — Smart Categorization
- **Masalah**: User malas pilih kategori setiap transaksi
- **Kenapa penting**: Mengurangi friction secara signifikan
- **User value**: App belajar dari kebiasaan dan auto-suggest kategori
- **UX expectation**:
  - Berdasarkan jam: pagi hari → auto-suggest "Kopi/Sarapan"
  - Berdasarkan nominal: Rp 15K → kemungkinan "Makan" atau "Minuman"
  - Berdasarkan merchant name (kalau user input notes): "GoFood" → "Makan"
  - Machine learning sederhana (rule-based dulu, bukan AI berat)
  - User bisa override, dan app belajar dari koreksi
  - Confidence indicator: "Yakin ini 'Makan'? (92%)"
- **Retention**: ★★★★☆
- **Trust**: ★★★★☆
- **Daily usage**: ★★★★★
- **Kompleksitas**: Sedang
- **Priority**: P1

---

#### F24 — Cashflow Trend & Anomaly Detection
- **Masalah**: User tidak sadar ada pola aneh di pengeluaran mereka
- **Kenapa penting**: Insight yang user tidak tahu mereka butuhkan
- **User value**: Deteksi pola pengeluaran yang tidak biasa
- **UX expectation**:
  - "Pengeluaran kamu minggu ini 40% lebih tinggi dari rata-rata"
  - "Kamu spend Rp 200K untuk transport bulan ini — 2x dari biasanya"
  - "Ada 3 transaksi di atas Rp 500K bulan ini — lebih banyak dari biasanya"
  - Tampilkan sebagai insight card, bukan alarm
  - Tone: informative, bukan alarmist
- **Retention**: ★★★★☆
- **Trust**: ★★★★★ (ini yang bikin user merasa "app ini pinter")
- **Daily usage**: ★★☆☆☆
- **Kompleksitas**: Sedang
- **Priority**: P2

---

#### F25 — Goal System (Target Tabungan)
- **Masalah**: User punya target finansial tapi tidak track progress
- **Kenapa penting**: Memberikan purpose di balik pencatatan
- **User value**: Set target dan lihat progress menuju goal
- **UX expectation**:
  - Buat goal: nama, target nominal, deadline (opsional)
  - Contoh: "Liburan ke Jepang — Rp 15.000.000"
  - Track progress: manual input "sudah nabung Rp X"
  - Visual: progress circle atau bar yang satisfying
  - Estimasi: "Dengan nabung Rp 500K/bulan, goal tercapai dalam 30 bulan"
  - Celebrate saat goal tercapai (subtle, meaningful)
  - Bisa pause atau archive goal
- **Retention**: ★★★★☆
- **Trust**: ★★★★☆
- **Daily usage**: ★★☆☆☆
- **Kompleksitas**: Sedang
- **Priority**: P2

---

#### F26 — Debt Tracker (Utang & Piutang)
- **Masalah**: User lupa siapa utang berapa, ke siapa, kapan harus bayar
- **Kenapa penting**: Utang adalah sumber stress finansial #1
- **User value**: Track utang dan piutang dengan jelas
- **UX expectation**:
  - Tambah utang: siapa, berapa, kapan due date, cicilan (opsional)
  - Visual timeline: "Rp 500K — due 15 Juni"
  - Reminder sebelum due date (3 hari, 1 hari)
  - Track pembayaran cicilan
  - Total utang vs piutang di dashboard
  - Tone: supportive, bukan judgmental (utang bukan dosa)
- **Retention**: ★★★☆☆
- **Trust**: ★★★★☆
- **Daily usage**: ★★☆☆☆
- **Kompleksitas**: Sedang
- **Priority**: P2

---

#### F27 — Export & Backup
- **Masalah**: User takut data hilang, butuh export untuk keperluan lain
- **Kenapa penting**: Trust dan data portability
- **User value**: Backup data dan export ke format lain
- **UX expectation**:
  - Export to CSV (untuk Google Sheets / Excel)
  - Export to PDF (monthly report)
  - Backup to file (encrypted, bisa disimpan di Google Drive manual)
  - Restore from backup
  - Auto-backup reminder (mingguan)
  - **Offline-first**: backup ke local storage dulu, user yang pilih upload ke cloud
- **Retention**: ★★★☆☆
- **Trust**: ★★★★★ (ini yang bikin user tenang)
- **Daily usage**: ★☆☆☆☆
- **Kompleksitas**: Rendah
- **Priority**: P1

---

#### F28 — Premium UI Polish
- **Masalah**: App finance kebanyakan terasa "enterprise" atau "murahan"
- **Kenapa penting**: Design quality = perceived value = retention
- **User value**: App yang enak dilihat dan dipakai
- **UX expectation**:
  - Material 3 dengan custom theming
  - Dark mode yang benar-benar dark (AMOLED friendly)
  - Smooth transitions (shared element, hero animations)
  - Haptic feedback yang meaningful (bukan random vibration)
  - Micro-interactions: angka yang count up, progress bar yang smooth
  - Custom numpad yang beautiful
  - Typography yang readable dan premium
  - Consistent spacing dan visual hierarchy
  - **Ini bukan "nice to have"** — ini yang bikin app terasa "beda kelas"
- **Retention**: ★★★★★
- **Trust**: ★★★★☆
- **Daily usage**: ★★★★★
- **Kompleksitas**: Sedang
- **Priority**: P0 (sebagian), P1 (sebagian)

---

### LEVEL 5: FUTURE / NICE TO HAVE

Fitur menarik tetapi tidak perlu diprioritaskan. Bisa bikin app bagus, tapi bukan penentu success.

---

#### F29 — Cloud Sync (Optional)
- **Masalah**: User ganti HP atau butuh akses dari device lain
- **Kenapa penting**: Untuk scaling, tapi bukan untuk V1
- **User value**: Data tersinkronisasi antar device
- **UX expectation**:
  - Opt-in, bukan default
  - End-to-end encryption
  - Conflict resolution: last-write-wins dengan preview
  - Sync indicator yang jelas
  - Bisa pilih untuk tetap offline-only
- **Retention**: ★★★☆☆
- **Trust**: ★★★☆☆ (beberapa user justru tidak mau cloud)
- **Daily usage**: ★☆☆☆☆
- **Kompleksitas**: Tinggi
- **Priority**: P3

---

#### F30 — Multi-Currency
- **Masalah**: User yang sering transaksi dalam mata uang asing
- **Kenapa penting**: Niche use case untuk Indonesia
- **User value**: Track transaksi dalam multiple currency
- **UX expectation**:
  - Set base currency (IDR)
  - Tambah transaksi dalam currency lain
  - Auto-convert dengan rate manual (karena offline-first)
  - Rate update saat online (opsional)
- **Retention**: ★★☆☆☆
- **Trust**: ★★★☆☆
- **Daily usage**: ★☆☆☆☆
- **Kompleksitas**: Sedang
- **Priority**: P3

---

#### F31 — Investment Tracking
- **Masalah**: User ingin track investasi alongside daily expenses
- **Kenapa penting**: Bukan core use case untuk target user
- **User value**: Lihat net worth termasuk investasi
- **UX expectation**:
  - Manual input: saham, reksa dana, crypto
  - Update nilai manual (karena offline-first)
  - Tampil di net worth overview
  - Jangan terlalu detail — bukan portfolio tracker
- **Retention**: ★★☆☆☆
- **Trust**: ★★★☆☆
- **Daily usage**: ★☆☆☆☆
- **Kompleksitas**: Sedang
- **Priority**: P3

---

#### F32 — Shared / Split Bills
- **Masalah**: User sering split bill dengan teman
- **Kenapa penting**: Bukan core use case untuk personal finance
- **User value**: Track siapa bayar apa
- **UX expectation**:
  - Split bill dengan nama orang
  - Track siapa utang berapa
  - Export summary untuk dikirim ke teman
  - Bukan pengganti Splitwise — lebih simpel
- **Retention**: ★★☆☆☆
- **Trust**: ★★☆☆☆
- **Daily usage**: ★★☆☆☆
- **Kompleksitas**: Sedang
- **Priority**: P3

---

#### F33 — AI-Powered Insights
- **Masalah**: User ingin insight yang lebih dalam dari data mereka
- **Kenapa penting**: Overhyped. Jangan buat dulu kalau rule-based sudah cukup.
- **User value**: Insight yang tidak bisa didapat dari manual analysis
- **UX expectation**:
  - "Berdasarkan 3 bulan terakhir, kamu spend rata-rata Rp X untuk makan"
  - "Kalau kamu kurangi makan luar 2x/minggu, kamu bisa hemat Rp Y/bulan"
  - "Pattern: kamu spend lebih banyak di weekend"
  - **Rule-based dulu, bukan LLM**. LLM = mahal, lambat, perlu internet
  - Kalau nanti pakai AI: on-device ML (TensorFlow Lite) untuk privacy
- **Retention**: ★★★☆☆
- **Trust**: ★★★☆☆
- **Daily usage**: ★★☆☆☆
- **Kompleksitas**: Tinggi
- **Priority**: P3

---

#### F34 — Widget (Home Screen)
- **Masalah**: User ingin lihat saldo atau quick input tanpa buka app
- **Kenapa penting**: Nice to have, tapi bukan penentu
- **User value**: Quick glance dan quick input dari home screen
- **UX expectation**:
  - Widget kecil: total balance + quick add button
  - Widget medium: balance + recent transactions + quick add
  - Tap quick add → langsung form input (bukan buka app dulu)
  - Update saat transaksi baru ditambahkan
- **Retention**: ★★★☆☆
- **Trust**: ★★★☆☆
- **Daily usage**: ★★★★☆
- **Kompleksitas**: Rendah
- **Priority**: P2

---

#### F35 — Custom Themes & Personalization
- **Masalah**: User ingin app terasa "mereka"
- **Kenapa penting**: Emotional connection, tapi bukan priority
- **User value**: App yang sesuai selera visual user
- **UX expectation**:
  - Light / Dark / AMOLED Black
  - Accent color picker
  - Custom icon pack (opsional)
  - Jangan terlalu banyak pilihan — paralysis by choice
- **Retention**: ★★☆☆☆
- **Trust**: ★★☆☆☆
- **Daily usage**: ★★★☆☆
- **Kompleksitas**: Rendah
- **Priority**: P3

---

## C. MVP RECOMMENDATION

### Fitur yang WAJIB ada sebelum launch (P0):

| # | Fitur | Alasan |
|---|-------|--------|
| 1 | Quick Transaction Input | Tanpa ini, app tidak dipakai |
| 2 | Basic Transaction Types | Fondasi data model |
| 3 | Account System | User perlu tahu saldo mereka |
| 4 | Category System | Fondasi insight dan budgeting |
| 5 | Transaction List & History | Tanpa history, app tidak ada gunanya |
| 6 | Dashboard / Home Screen | First impression setiap hari |
| 7 | Local Database (Room) | Offline-first requirement |
| 8 | PIN / Biometric Lock | Trust non-negotiable |
| 9 | Monthly Budget | Core value proposition |
| 10 | Recurring Transactions | Mengurangi friction signifikan |
| 11 | Transaction Templates | Kunci untuk <3 detik input |
| 12 | Search & Filter | Produktivitas dasar |
| 13 | Monthly Summary | Insight pertama user |
| 14 | Smart Reminder | Retention dari hari 1 |
| 15 | Premium UI Polish (basic) | Design quality = perceived value |

**Total: 15 fitur untuk V1 launch**

### Yang TIDAK boleh ada di V1:
- Cloud sync (P3)
- Multi-currency (P3)
- Investment tracking (P3)
- Split bills (P3)
- AI insights (P3)
- Debt tracker (P2 — bisa P1 kalau target user punya pain point ini)
- Goal system (P2)
- Widget (P2)

**OPINI TEGAS**: Jangan goda untuk tambah fitur "keren" di V1. MVP yang fokus dan polished > MVP yang fitur banyak tapi setengah-setengah. User akan forgive app yang simpel tapi works. User akan NOT forgive app yang banyak fitur tapi buggy.

---

## D. PHASE ROADMAP

### V1 — LAUNCH (Month 1-3)
**Theme**: "Works beautifully for daily use"

**Scope**:
- F01 Quick Transaction Input
- F02 Basic Transaction Types
- F03 Account System
- F04 Category System
- F05 Transaction List & History
- F06 Dashboard / Home Screen
- F07 Local Database (Room)
- F08 PIN / Biometric Lock
- F09 Monthly Budget
- F10 Recurring Transactions
- F11 Transaction Templates
- F15 Search & Filter
- F16 Monthly Summary
- F17 Smart Reminder
- F28 Premium UI Polish (basic: Material 3, dark mode, smooth transitions)

**Success metrics**:
- Day 1 retention > 60%
- Day 7 retention > 35%
- Average transactions per user per day > 2
- Crash-free rate > 99.5%
- APK size < 25MB

**Tech stack recommendation**:
- Kotlin + Jetpack Compose
- Room Database
- DataStore (preferences)
- WorkManager (recurring transactions, reminders)
- Biometric API
- Material 3
- Hilt/Dagger (DI)
- Coroutines + Flow

---

### V2 — RETENTION (Month 4-6)
**Theme**: "Makes you come back every day"

**Scope**:
- F12 Split Transaction
- F13 Tags System
- F14 Notes & Receipt Attachment
- F18 Weekly Check-in
- F19 Streak System (redesigned, anti-guilt)
- F20 Anti Drop-off System
- F21 Progress Feedback
- F22 Anti-Boncos System
- F23 Smart Categorization
- F27 Export & Backup
- F28 Premium UI Polish (advanced: micro-interactions, haptic, animations)

**Success metrics**:
- Day 30 retention > 25%
- Average session duration > 2 minutes
- Weekly active users / Monthly active users > 0.6
- Export/backup usage > 20% of users

---

### V3 — DEPTH (Month 7-9)
**Theme**: "Becomes your financial companion"

**Scope**:
- F24 Cashflow Trend & Anomaly Detection
- F25 Goal System
- F26 Debt Tracker
- F34 Widget (Home Screen)
- F28 Premium UI Polish (complete: custom themes, advanced animations)

**Success metrics**:
- Goal creation rate > 15% of users
- Debt tracker usage > 10% of users
- Widget adoption > 20% of users
- NPS > 50

---

### V4+ — WORLD-CLASS (Month 10+)
**Theme**: "Hard to leave, impossible to replace"

**Scope**:
- F29 Cloud Sync (optional, E2E encrypted)
- F30 Multi-Currency
- F31 Investment Tracking
- F32 Shared / Split Bills
- F33 AI-Powered Insights (on-device ML)
- F35 Custom Themes & Personalization

**Success metrics**:
- Cloud sync adoption > 30% of users
- Multi-currency usage > 5% of users
- User referrals > 15% of new users
- App store rating > 4.7

---

## E. BIGGEST PRODUCT MISTAKES TO AVOID

### 1. ❌ Feature Bloat di V1
**Mistake**: Tambah semua fitur karena "kompetitor punya"
**Reality**: User tidak butuh 50 fitur. User butuh 5 fitur yang works perfectly.
**Fix**: Launch dengan 15 fitur P0, polish sampai sempurna, baru tambah.

### 2. ❌ Cloud-First Architecture
**Mistake**: Bangun app yang butuh internet untuk fungsi dasar
**Reality**: Target user punya internet tidak stabil. Offline-first bukan optional.
**Fix**: Room database sebagai source of truth. Cloud hanyalah enhancement.

### 3. ❌ Subscription di Awal
**Mistake**: Langsung charge subscription dari V1
**Reality**: User tidak akan bayar untuk app yang belum terbukti berguna
**Fix**: Free untuk semua fitur core. Premium hanya untuk cloud sync dan advanced features nanti.

### 4. ❌ Over-Engineering AI
**Mistake**: Pakai LLM untuk categorization dan insights
**Reality**: Mahal, lambat, perlu internet, dan overkill untuk personal finance
**Fix**: Rule-based smart categorization dulu. ML on-device kalau memang perlu nanti.

### 5. ❌ Streak yang Toxic
**Mistake**: Streak yang bikin user merasa gagal kalau putus
**Reality**: User akan uninstall karena merasa "gagal", bukan karena app tidak berguna
**Fix**: Streak dengan freeze, no guilt trip, celebratory bukan judgmental.

### 6. ❌ Mengabaikan Input Speed
**Mistake**: Form transaksi yang panjang dan lambat
**Reality**: Ini adalah #1 reason user stop using finance apps
**Fix**: <3 detik adalah requirement, bukan target. Custom numpad, smart defaults, templates.

### 7. ❌ Budget yang Rigid
**Mistake**: Budget seperti YNAB yang memaksa user allocate setiap rupiah
**Reality**: Target user tidak mau accounting. Mereka mau awareness.
**Fix**: Budget sebagai "guardrail", bukan "cage". Warning, bukan enforcement.

### 8. ❌ Ignoring Empty States
**Mistake**: Empty state = "No data" yang depressing
**Reality**: Empty state adalah first impression untuk new user
**Fix**: Empty state yang encouraging, educational, dan actionable.

### 9. ❌ No Data Loss Prevention
**Mistake**: Tidak ada backup strategy
**Reality**: User akan PANIK kalau data hilang. Satu insiden = uninstall + bad review.
**Fix**: Auto-backup reminder, export to file, clear restore flow.

### 10. ❌ Generic UX
**Mistake**: UI yang terasa seperti app finance lainnya
**Reality**: Tidak ada alasan user pilih NyatetDuwit kalau terasa sama
**Fix**: Design personality yang jelas — warm, smart, not judgmental. Micro-interactions yang delightful.

---

## F. FINAL BRUTALLY HONEST RECOMMENDATION

### 1. Fitur apa yang WAJIB ada sebelum launch?
Quick input (<3 detik), multi-akun, kategori, budget bulanan, recurring, templates, dashboard, search, monthly summary, reminder, biometric lock, dan UI yang polished. Itu saja. 15 fitur. Jangan lebih.

### 2. Fitur apa yang paling meningkatkan retensi?
**Smart Reminder + Anti-Boncos System + Weekly Check-in**. Kombinasi ini membuat user merasa "app ini peduli sama keuangan saya" tanpa terasa annoying.

### 3. Fitur apa yang paling terasa premium?
**Anti-Boncos System + Smart Categorization + Premium UI Polish**. Ini yang bikin user bilang "wah, app ini pinter dan cantik". Bukan AI, bukan cloud sync.

### 4. Fitur apa yang overrated dan jangan dibuat dulu?
- AI insights (rule-based sudah cukup untuk 2 tahun pertama)
- Cloud sync (user offline-first tidak butuh ini di awal)
- Investment tracking (bukan core use case)
- Multi-currency (niche banget untuk target user)
- Split bills (bukan personal finance)

### 5. Jika harus memangkas scope 50%, apa yang dipotong?
Potong: Split transaction, tags, receipt attachment, streak system, weekly check-in, debt tracker, goal system, anomaly detection, export to PDF, dan semua fitur P2/P3.

**Tetap pertahankan**: Quick input, transaction types, accounts, categories, history, dashboard, database, biometric, budget, recurring, templates, search, monthly summary, reminder, UI polish.

### 6. 10 keputusan produk paling penting untuk membuat NyatetDuwit terasa "beda kelas"

| # | Keputusan | Kenapa |
|---|-----------|--------|
| 1 | Offline-first sebagai arsitektur utama, bukan afterthought | User bisa percaya app selalu works |
| 2 | Input <3 detik sebagai non-negotiable requirement | Friction = death untuk finance app |
| 3 | Custom numpad dengan preset nominal | Mengurangi 2-3 tap per transaksi |
| 4 | Smart defaults (kategori terakhir, akun terakhir, jam-based suggestion) | User tidak perlu pikir setiap transaksi |
| 5 | Budget sebagai awareness, bukan enforcement | Tidak bikin user merasa dihakimi |
| 6 | Anti-boncos system dengan daily spending allowance | Insight yang actionable setiap hari |
| 7 | Streak dengan freeze dan no guilt trip | Gamifikasi yang tidak toxic |
| 8 | Empathetic copywriting di semua notifikasi dan empty state | App terasa seperti teman, bukan bos |
| 9 | Premium UI dengan micro-interactions yang meaningful | Perceived value = retention |
| 10 | Data portability (export/backup) sebagai first-class feature | Trust bahwa data user adalah milik user |

### 7. Apa yang membuat user uninstall aplikasi finance?

| Rank | Alasan | Persentase (estimasi) |
|------|--------|----------------------|
| 1 | Terlalu ribet input (lebih dari 5 tap per transaksi) | 35% |
| 2 | Lupa pakai (tidak ada reminder/habit system) | 25% |
| 3 | Tidak merasa benefit setelah 2 minggu | 15% |
| 4 | App crash atau data hilang | 10% |
| 5 | Terlalu banyak notifikasi / nagging | 8% |
| 6 | UI jelek / terasa murahan | 4% |
| 7 | Subscription terlalu mahal | 3% |

### 8. Bagaimana membuat NyatetDuwit jadi app yang benar-benar dipakai tiap hari?

**Formula**: Fast Input + Timely Reminder + Actionable Insight + No Guilt

1. **Hari 1-3**: User experience harus flawless. Quick input works, dashboard jelas, reminder gentle.
2. **Hari 4-7**: User mulai lihat pattern. Monthly summary pertama muncul. "Oh ternyata aku spend X untuk makan."
3. **Minggu 2**: Anti-boncos system mulai aktif. "Oh, daily allowance aku tinggal Rp 50K." User mulai adjust behavior.
4. **Minggu 3-4**: Habit terbentuk. User otomatis buka app setelah transaksi. Streak mulai terbentuk (dengan freeze).
5. **Bulan 2**: User tidak bisa bayangin hidup tanpa app. Data sudah banyak, insight sudah meaningful. Switching cost tinggi.

**Kunci**: Jangan bikin user merasa "wajib". Bikin user merasa "pengin". Ada bedanya.

---

## OFFLINE-FIRST ARCHITECTURE RECOMMENDATION

### Data Architecture

```
┌─────────────────────────────────────────────────┐
│                   UI Layer                       │
│         (Jetpack Compose + ViewModel)            │
└─────────────────────┬───────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────┐
│               Domain Layer                       │
│         (Use Cases + Business Logic)             │
└─────────────────────┬───────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────┐
│               Data Layer                         │
│    ┌─────────────┐    ┌─────────────────────┐   │
│    │   Room DB   │    │   DataStore         │   │
│    │ (Transactions│    │ (Preferences,       │   │
│    │  Accounts,   │    │  Settings,          │   │
│    │  Categories) │    │  Streak, etc)       │   │
│    └─────────────┘    └─────────────────────┘   │
│    ┌─────────────┐    ┌─────────────────────┐   │
│    │   File      │    │   WorkManager       │   │
│    │   Storage   │    │ (Recurring,         │   │
│    │ (Backups,   │    │  Reminders,          │   │
│    │  Images)    │    │  Auto-backup)        │   │
│    └─────────────┘    └─────────────────────┘   │
└─────────────────────────────────────────────────┘
```

### Room Database Schema (Core Tables)

```sql
-- Accounts
accounts (id, name, type, balance, icon, color, is_hidden, order_index, created_at, updated_at)

-- Categories
categories (id, name, type, icon, color, parent_id, is_default, order_index, created_at)

-- Transactions
transactions (id, type, amount, account_id, category_id, notes, date_time, created_at, updated_at)

-- Transaction Splits
transaction_splits (id, transaction_id, category_id, amount, notes)

-- Transaction Tags
transaction_tags (id, transaction_id, tag_name)

-- Recurring Transactions
recurring_transactions (id, template_transaction_id, frequency, start_date, end_date, next_due, is_active, last_processed)

-- Templates
templates (id, type, amount, category_id, account_id, notes, name, usage_count, last_used, created_at)

-- Budgets
budgets (id, category_id, amount, period, start_date, end_date, is_active)

-- Goals
goals (id, name, target_amount, current_amount, deadline, icon, color, is_active, created_at)

-- Debts
debts (id, type, person_name, amount, remaining_amount, due_date, notes, is_active, created_at)

-- Debt Payments
debt_payments (id, debt_id, amount, date, notes)
```

### Sync Strategy (Future)

1. **Local-first**: Room DB adalah source of truth
2. **Opt-in sync**: User harus explicitly enable cloud sync
3. **E2E encryption**: Data di-encrypt di device sebelum upload
4. **Conflict resolution**: Last-write-wins dengan preview perubahan
5. **Delta sync**: Hanya sync perubahan, bukan full data
6. **Background sync**: WorkManager dengan network constraint (WiFi only default)
7. **Manual sync trigger**: Pull to refresh di settings

### Backup Strategy

1. **Local backup**: Auto-backup ke internal storage setiap minggu
2. **Export to file**: User bisa export ke CSV/PDF kapan saja
3. **Manual cloud backup**: User pilih file backup untuk upload ke Google Drive
4. **Restore flow**: Import dari file backup, dengan preview data sebelum overwrite
5. **Encryption**: Backup file di-encrypt dengan PIN user

### Storage Consideration

- **Room DB**: ~5-10MB untuk 10.000 transaksi
- **Images**: Compress ke max 500KB each. 100 receipts = ~50MB
- **Backups**: CSV ~1-2MB, encrypted backup ~2-3MB
- **Total estimated**: <100MB untuk power user (1 tahun pemakaian)
- **APK size target**: <25MB

### Conflict Handling (Future Cloud Sync)

1. **Timestamp-based**: Last-write-wins untuk field yang sama
2. **Merge strategy**: Untuk field berbeda, merge keduanya
3. **User preview**: Tampilkan conflict sebelum resolve
4. **Local wins default**: Kalau user tidak sure, local data di-prioritaskan
5. **Audit log**: Track semua perubahan untuk debugging

---

## UX & DESIGN PRINCIPLES

### "Bagaimana caranya supaya user tidak capek nyatet?"

#### 1. Minimum Taps Principle
- Target: **max 3 taps** untuk transaksi sederhana
- Flow: Buka app → tap nominal → tap kategori → done
- Default values: akun terakhir, kategori terakhir, tanggal hari ini

#### 2. Smart Defaults
- Jam 6-10 pagi → suggest "Sarapan/Kopi"
- Jam 11-14 → suggest "Makan Siang"
- Jam 17-21 → suggest "Makan Malam"
- Nominal Rp 10K-25K → kemungkinan "Makan/Minuman"
- Nominal Rp 50K-150K → kemungkinan "Belanja/Transport"

#### 3. Preset Nominal
- Custom numpad dengan chip: 5K, 10K, 20K, 50K, 100K
- Quick increment: +10K, +50K, +100K buttons
- Recent nominal: tampilkan 3 nominal terakhir yang sering dipakai

#### 4. Swipe Actions
- Swipe left/right di transaction list: edit/delete
- Swipe di input screen: switch income/expense
- Swipe di dashboard: navigate ke monthly summary

#### 5. Habit-Based UX
- Input screen selalu accessible (floating button di semua screen)
- Quick add dari notification (tanpa buka app)
- Widget untuk quick input (V3)
- "Continue last transaction" option

#### 6. Empty States That Encourage
- Bukan "No transactions yet"
- Tapi "Yuk mulai nyatet! Tap tombol + di bawah untuk transaksi pertamamu 🚀"
- Dengan ilustrasi yang warm dan inviting

#### 7. Delightful Interactions
- Angka yang count up saat input nominal
- Progress bar yang smooth dan satisfying
- Haptic feedback yang subtle tapi meaningful
- Transitions yang fluid (shared element, hero animations)
- Celebration yang subtle saat milestone (bukan confetti berlebihan)

#### 8. Error States That Help
- Bukan "Error occurred"
- Tapi "Hmm, sepertinya ada yang salah. Data kamu aman kok. Mau coba lagi?"
- Dengan action button yang jelas

---

## FEATURE HIERARCHY SUMMARY TABLE

| Level | Fitur | Priority | Complexity | Retention Impact | Launch Phase |
|-------|-------|----------|------------|------------------|--------------|
| **FOUNDATION** | Quick Transaction Input | P0 | Sedang | ★★★★★ | V1 |
| | Basic Transaction Types | P0 | Rendah | ★★★★★ | V1 |
| | Account System | P0 | Rendah | ★★★★☆ | V1 |
| | Category System | P0 | Rendah | ★★★★☆ | V1 |
| | Transaction List & History | P0 | Rendah | ★★★★☆ | V1 |
| | Dashboard / Home Screen | P0 | Rendah | ★★★★★ | V1 |
| | Local Database (Room) | P0 | Sedang | ★★★★★ | V1 |
| | PIN / Biometric Lock | P0 | Rendah | ★★★☆☆ | V1 |
| **CORE** | Monthly Budget | P0 | Rendah | ★★★★★ | V1 |
| | Recurring Transactions | P0 | Sedang | ★★★★☆ | V1 |
| | Transaction Templates | P0 | Rendah | ★★★★☆ | V1 |
| | Split Transaction | P1 | Sedang | ★★★☆☆ | V2 |
| | Tags System | P1 | Rendah | ★★★☆☆ | V2 |
| | Notes & Attachment | P1 | Sedang | ★★☆☆☆ | V2 |
| | Search & Filter | P0 | Sedang | ★★★☆☆ | V1 |
| | Monthly Summary | P0 | Sedang | ★★★★★ | V1 |
| **RETENTION** | Smart Reminder | P0 | Rendah | ★★★★★ | V1 |
| | Weekly Check-in | P1 | Rendah | ★★★★☆ | V2 |
| | Streak System | P1 | Rendah | ★★★★☆ | V2 |
| | Anti Drop-off System | P1 | Rendah | ★★★★★ | V2 |
| | Progress Feedback | P1 | Rendah | ★★★★☆ | V2 |
| **PREMIUM** | Anti-Boncos System | P1 | Sedang | ★★★★★ | V2 |
| | Smart Categorization | P1 | Sedang | ★★★★☆ | V2 |
| | Cashflow & Anomaly | P2 | Sedang | ★★★★☆ | V3 |
| | Goal System | P2 | Sedang | ★★★★☆ | V3 |
| | Debt Tracker | P2 | Sedang | ★★★☆☆ | V3 |
| | Export & Backup | P1 | Rendah | ★★★☆☆ | V2 |
| | Premium UI Polish | P0/P1 | Sedang | ★★★★★ | V1-V3 |
| **FUTURE** | Cloud Sync | P3 | Tinggi | ★★★☆☆ | V4+ |
| | Multi-Currency | P3 | Sedang | ★★☆☆☆ | V4+ |
| | Investment Tracking | P3 | Sedang | ★★☆☆☆ | V4+ |
| | Split Bills | P3 | Sedang | ★★☆☆☆ | V4+ |
| | AI Insights | P3 | Tinggi | ★★★☆☆ | V4+ |
| | Widget | P2 | Rendah | ★★★☆☆ | V3 |
| | Custom Themes | P3 | Rendah | ★★☆☆☆ | V4+ |

---

> **Final Word**: NyatetDuwit tidak perlu menjadi aplikasi paling lengkap. NyatetDuwit perlu menjadi aplikasi yang paling **sering dipakai**. Dan itu hanya bisa dicapai dengan: **cepat, simpel, reliable, dan terasa seperti dibuat oleh orang yang benar-benar paham masalah user.**
>
> Build less. Polish more. Ship fast. Iterate faster.
