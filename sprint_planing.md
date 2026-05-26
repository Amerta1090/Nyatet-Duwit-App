# NyatetDuwit — Sprint Planning

> **Platform**: Android (Kotlin + Jetpack Compose)
> **Architecture**: MVVM + Clean Architecture
> **Database**: Room (Offline-First)
> **Team assumption**: 1-2 developers (indie/small team)
> **Sprint duration**: 2 minggu per sprint

---

## SPRINT 0 — SETUP & FOUNDATION (Week 1-2)

**Goal**: Project structure, tech stack, dan core infrastructure siap.

### Sprint Backlog

| ID | Task | Est. (hari) | Priority | Notes |
|----|------|-------------|----------|-------|
| S0.1 | Setup Android project (Kotlin, Compose, Gradle) | 1 | P0 | Material 3, Compose BOM terbaru |
| S0.2 | Setup Clean Architecture modules (data, domain, presentation) | 2 | P0 | Multi-module kalau perlu, tapi single module juga oke untuk awal |
| S0.3 | Setup Hilt/Dagger DI | 1 | P0 | Dependency injection dari awal |
| S0.4 | Setup Room Database + Migrations | 2 | P0 | Schema core tables (accounts, categories, transactions) |
| S0.5 | Setup DataStore (preferences) | 0.5 | P0 | Untuk settings, theme, dll |
| S0.6 | Setup Navigation Compose | 1 | P0 | Single activity, composable navigation |
| S0.7 | Design system: colors, typography, spacing, shapes | 2 | P0 | Light + Dark theme dari awal |
| S0.8 | Setup Coroutines + Flow architecture | 1 | P0 | Repository pattern dengan Flow |
| S0.9 | Setup testing infrastructure (unit + UI test) | 1 | P0 | Minimal setup, test ditulis seiring development |
| S0.10 | CI/CD setup (GitHub Actions) | 1 | P1 | Build, lint, test automation |

### Deliverables
- [ ] Project structure siap
- [ ] Room database dengan schema awal
- [ ] DI container configured
- [ ] Navigation scaffold
- [ ] Design system tokens
- [ ] Dark mode working

### Risk
- Over-engineering architecture di awal → keep it simple
- Multi-module terlalu kompleks untuk 1-2 dev → start single module, split nanti kalau perlu

---

## SPRINT 1 — ACCOUNT & CATEGORY SYSTEM (Week 3-4)

**Goal**: User bisa setup akun dan kategori. Fondasi data model lengkap.

### Sprint Backlog

| ID | Task | Est. (hari) | Priority | Notes |
|----|------|-------------|----------|-------|
| S1.1 | Account entity + DAO + Repository | 2 | P0 | CRUD accounts |
| S1.2 | Account UI: list, add, edit, delete | 3 | P0 | Compose screen dengan form |
| S1.3 | Account type icons & colors (Cash, Bank, E-Wallet) | 1 | P0 | Icon resource + color mapping |
| S1.4 | Category entity + DAO + Repository | 2 | P0 | CRUD categories, support parent-child |
| S1.5 | Category UI: list, add, edit, delete | 3 | P0 | Compose screen dengan icon picker |
| S1.6 | Default categories seed data | 1 | P0 | ~20 categories relevan untuk Indonesia |
| S1.7 | Account balance calculation logic | 1 | P0 | Balance = sum of transactions |
| S1.8 | Account reorder (drag & drop) | 1 | P1 | Bisa delay ke Sprint 2 kalau waktu kurang |

### Deliverables
- [ ] User bisa tambah/edit/hapus akun
- [ ] User bisa tambah/edit/hapus kategori
- [ ] Default categories ter-seed saat first launch
- [ ] Balance per akun ter-calculate

### Default Categories (Seed Data)

**Pengeluaran:**
- 🍔 Makan & Minuman
- 🚗 Transport
- 🛒 Belanja
- 💡 Tagihan (Listrik, Air, Internet)
- 🏠 Rumah & Kos
- 🎮 Hiburan
- 💊 Kesehatan
- 📚 Edukasi
- 👕 Fashion
- 🎁 Sosial & Hadiah
- 📦 Lainnya

**Pemasukan:**
- 💰 Gaji
- 💼 Freelance
- 📈 Investasi
- 🎁 Hadiah
- 💵 Lainnya

### Acceptance Criteria
- [ ] Tambah akun baru → muncul di list dengan icon & balance 0
- [ ] Tambah kategori → muncul di picker saat input transaksi
- [ ] Hapus akun → konfirmasi, tidak boleh kalau masih ada transaksi
- [ ] Edit akun → nama, icon, balance awal bisa diubah
- [ ] Default categories tidak bisa dihapus (bisa di-hide)

---

## SPRINT 2 — TRANSACTION CORE (Week 5-6)

**Goal**: User bisa input transaksi (income, expense, transfer) dengan cepat.

### Sprint Backlog

| ID | Task | Est. (hari) | Priority | Notes |
|----|------|-------------|----------|-------|
| S2.1 | Transaction entity + DAO + Repository | 2 | P0 | Support income, expense, transfer |
| S2.2 | Transaction form UI (Income) | 3 | P0 | Numpad, kategori picker, akun picker |
| S2.3 | Transaction form UI (Expense) | 2 | P0 | Reuse income form, beda default values |
| S2.4 | Transaction form UI (Transfer) | 2 | P0 | From account → to account, balance update |
| S2.5 | Custom Numpad component | 3 | P0 | Dengan preset chips (5K, 10K, 20K, 50K, 100K) |
| S2.6 | Transaction list UI (grouped by date) | 3 | P0 | Infinite scroll, swipe actions |
| S2.7 | Transaction detail screen | 2 | P0 | View + edit + delete |
| S2.8 | Balance update logic (auto-update saat transaksi) | 1 | P0 | Triggered by transaction CRUD |
| S2.9 | Undo delete (snackbar 3 detik) | 1 | P0 | Soft delete dengan timer |

### Deliverables
- [ ] User bisa input income, expense, transfer
- [ ] Custom numpad dengan preset nominal
- [ ] Transaction list grouped by date
- [ ] Balance auto-update
- [ ] Swipe to edit/delete
- [ ] Undo delete

### Acceptance Criteria
- [ ] Input expense → balance akun berkurang, kategori tercatat
- [ ] Input income → balance akun bertambah
- [ ] Transfer → balance akun asal berkurang, akun tujuan bertambah
- [ ] Delete transaksi → balance kembali seperti semula
- [ ] Swipe left → edit, swipe right → delete (dengan confirm)
- [ ] Undo delete dalam 3 detik → transaksi kembali

### UX Target
- **Input time**: <3 detik untuk transaksi sederhana
- **Taps**: Max 3-4 taps per transaksi
- **Animation**: Smooth, no jank

---

## SPRINT 3 — DASHBOARD & HOME (Week 7-8)

**Goal**: Home screen yang informatif dan quick input yang selalu accessible.

### Sprint Backlog

| ID | Task | Est. (hari) | Priority | Notes |
|----|------|-------------|----------|-------|
| S3.1 | Dashboard screen layout | 3 | P0 | Total balance, income/expense summary, recent transactions |
| S3.2 | Total balance card (show/hide toggle) | 1 | P0 | Eye icon untuk toggle visibility |
| S3.3 | Income vs Expense summary (current month) | 2 | P0 | Card dengan progress indicator |
| S3.4 | Top 3 categories (current month) | 2 | P0 | Horizontal list dengan icon & amount |
| S3.5 | Recent transactions (5 terakhir) | 1 | P0 | Mini list di dashboard |
| S3.6 | Floating Action Button (quick input) | 1 | P0 | Always visible, expandable (income/expense/transfer) |
| S3.7 | Empty state untuk dashboard | 1 | P0 | Encouraging copy + CTA |
| S3.8 | Pull to refresh (visual feedback) | 0.5 | P1 | Walaupun offline, tetap kasih feedback |

### Deliverables
- [ ] Dashboard dengan semua komponen
- [ ] Quick input FAB
- [ ] Empty state yang encouraging
- [ ] Balance show/hide

### Acceptance Criteria
- [ ] Dashboard load <1 detik (data dari Room, instant)
- [ ] FAB selalu visible di semua scroll position
- [ ] Tap FAB → langsung buka transaction form
- [ ] Toggle hide balance → angka jadi "****"
- [ ] Empty state muncul kalau belum ada transaksi

---

## SPRINT 4 — BUDGET & RECURRING (Week 9-10)

**Goal**: User bisa set budget dan recurring transactions.

### Sprint Backlog

| ID | Task | Est. (hari) | Priority | Notes |
|----|------|-------------|----------|-------|
| S4.1 | Budget entity + DAO + Repository | 2 | P0 | Budget per kategori atau total |
| S4.2 | Budget setup UI | 3 | P0 | Set nominal, pilih kategori atau total |
| S4.3 | Budget progress UI (progress bar + color coding) | 2 | P0 | Hijau → Kuning → Merah |
| S4.4 | Budget warning logic (80%, 100%) | 1 | P0 | Local notification saat threshold tercapai |
| S4.5 | Recurring transaction entity + DAO | 2 | P0 | Frequency, start/end date, next due |
| S4.6 | Recurring transaction setup UI | 2 | P0 | Dari transaksi existing atau buat baru |
| S4.7 | WorkManager untuk auto-create recurring | 2 | P0 | Daily check, create transaction saat due |
| S4.8 | Recurring preview & management UI | 1 | P1 | List recurring aktif, bisa pause/skip |

### Deliverables
- [ ] User bisa set budget (total atau per kategori)
- [ ] Budget progress visible di dashboard/budget screen
- [ ] Warning saat 80% dan 100% budget terpakai
- [ ] Recurring transactions auto-created saat due date
- [ ] User bisa skip atau pause recurring

### Acceptance Criteria
- [ ] Set budget Rp 2.000.000 → progress bar update setiap transaksi
- [ ] 80% terpakai → notification "Budget kamu udah 80% nih"
- [ ] 100% terpakai → notification "Budget habis! Hati-hati ya"
- [ ] Recurring bulanan → otomatis create transaction di tanggal yang sama
- [ ] Skip recurring → instance yang ini di-skip, yang lain tetap jalan

---

## SPRINT 5 — TEMPLATES & SEARCH (Week 11-12)

**Goal**: Transaksi super cepat dengan templates. Search & filter yang powerful.

### Sprint Backlog

| ID | Task | Est. (hari) | Priority | Notes |
|----|------|-------------|----------|-------|
| S5.1 | Template entity + DAO + Repository | 1.5 | P0 | Save transaction as template |
| S5.2 | Save as template (long press transaction) | 1.5 | P0 | Context menu → save as template |
| S5.3 | Template picker di transaction form | 2 | P0 | Chips/suggestions di form input |
| S5.4 | Template management UI | 1.5 | P0 | List, edit, delete, pin favorite |
| S5.5 | Search functionality (Room FTS or LIKE query) | 2 | P0 | Search by nominal, category, notes |
| S5.6 | Search UI (search bar + results) | 2 | P0 | Highlight matches, recent searches |
| S5.7 | Filter UI (date, category, account, type) | 2 | P0 | Bottom sheet atau dedicated screen |
| S5.8 | Filter combination logic | 1 | P0 | Multiple filters bisa di-combine |

### Deliverables
- [ ] User bisa save transaction sebagai template
- [ ] Template muncul di quick input
- [ ] Global search working
- [ ] Filter dengan kombinasi multiple criteria

### Acceptance Criteria
- [ ] Long press transaksi → "Save as Template" → template tersimpan
- [ ] Buka form input → template chips muncul di atas
- [ ] Tap template → form ter-fill otomatis, tinggal confirm
- [ ] Search "makan" → muncul semua transaksi dengan kategori Makan
- [ ] Search "50000" → muncul semua transaksi Rp 50.000
- [ ] Filter: last 7 days + kategori Makan → hasil sesuai

---

## SPRINT 6 — MONTHLY SUMMARY & REMINDER (Week 13-14)

**Goal**: User bisa review keuangan bulanan. Reminder untuk habit formation.

### Sprint Backlog

| ID | Task | Est. (hari) | Priority | Notes |
|----|------|-------------|----------|-------|
| S6.1 | Monthly aggregation query (Room) | 2 | P0 | Sum by category, income vs expense |
| S6.2 | Monthly summary screen | 3 | P0 | Charts, top categories, comparison |
| S6.3 | Month-to-month comparison logic | 1.5 | P0 | ↑↓ percentage vs previous month |
| S6.4 | Spending trend chart (line chart) | 2 | P0 | Simple line chart, bisa pakai custom atau library ringan |
| S6.5 | Smart reminder setup (WorkManager + Notification) | 2 | P0 | Local notification, configurable time |
| S6.6 | Adaptive reminder logic | 1.5 | P0 | Kurangi frekuensi kalau user sudah rajin |
| S6.7 | Reminder settings UI | 1 | P0 | Enable/disable, time picker, frequency |
| S6.8 | Shareable monthly summary (image export) | 2 | P1 | Screenshot-style summary untuk social media |

### Deliverables
- [ ] Monthly summary screen dengan charts
- [ ] Month-to-month comparison
- [ ] Smart reminder working
- [ ] Adaptive reminder frequency

### Acceptance Criteria
- [ ] Buka monthly summary → total income, expense, top categories visible
- [ ] Comparison: "Naik 15% dari bulan lalu" atau "Turun 10%"
- [ ] Reminder muncul di waktu yang diset (default: 8 malam)
- [ ] Kalau user sudah catat 5 hari berturut-turut → reminder jadi 1x/hari
- [ ] Kalau user tidak catat 3 hari → reminder jadi 2x/hari

### Chart Library Recommendation
- **Vico** (Compose native, ringan, customizable) — RECOMMENDED
- **Charts** (by Philipp Jahoda) — bagus tapi XML-based
- Custom Compose canvas — paling ringan tapi effort lebih

---

## SPRINT 7 — SECURITY & POLISH (Week 15-16)

**Goal**: App aman, polished, dan siap launch.

### Sprint Backlog

| ID | Task | Est. (hari) | Priority | Notes |
|----|------|-------------|----------|-------|
| S7.1 | Biometric authentication setup | 2 | P0 | Fingerprint + Face unlock |
| S7.2 | PIN fallback setup | 2 | P0 | 6-digit PIN, setup di onboarding |
| S7.3 | App lock on background (lifecycle aware) | 1.5 | P0 | Lock saat app kembali dari background |
| S7.4 | Lock timeout settings | 0.5 | P0 | Configurable: instant, 1 min, 5 min, 15 min |
| S7.5 | Onboarding flow | 3 | P0 | Welcome → setup akun → setup kategori → setup budget → done |
| S7.6 | Haptic feedback integration | 1 | P0 | Di semua interactive elements |
| S7.7 | Micro-interactions (count up, smooth progress) | 2 | P0 | Animasi angka, progress bar, transitions |
| S7.8 | Shared element transitions | 1.5 | P0 | Transaction list → detail |
| S7.9 | Performance optimization | 2 | P0 | Lazy loading, image compression, query optimization |
| S7.10 | Crash reporting setup (Firebase Crashlytics) | 1 | P0 | Anonymous, opt-in |

### Deliverables
- [ ] Biometric + PIN lock working
- [ ] Onboarding flow complete
- [ ] Haptic feedback di semua interaksi
- [ ] Micro-interactions polished
- [ ] Performance optimized
- [ ] Crash reporting active

### Acceptance Criteria
- [ ] Buka app → biometric prompt muncul (<1 detik)
- [ ] Biometric gagal → PIN fallback muncul
- [ ] App ke background → lock setelah timeout yang diset
- [ ] Onboarding selesai → user langsung di dashboard dengan akun siap pakai
- [ ] Haptic feedback terasa di setiap tap, swipe, confirm
- [ ] App launch time <2 detik (cold start)

---

## SPRINT 8 — LAUNCH PREP (Week 17-18)

**Goal**: App siap release ke Play Store.

### Sprint Backlog

| ID | Task | Est. (hari) | Priority | Notes |
|----|------|-------------|----------|-------|
| S8.1 | Export & Backup (CSV) | 2 | P0 | Export transactions to CSV |
| S8.2 | Backup to file (encrypted) | 2 | P0 | Full database backup, encrypted dengan PIN |
| S8.3 | Restore from backup | 2 | P0 | Import backup file, preview sebelum overwrite |
| S8.4 | Settings screen | 2 | P0 | All settings in one place |
| S8.5 | About screen + privacy policy | 1 | P0 | Legal requirement |
| S8.6 | App icon + splash screen | 1 | P0 | Adaptive icon, branded splash |
| S8.7 | Play Store assets (screenshots, description) | 2 | P0 | 8 screenshots, description, feature graphic |
| S8.8 | Beta testing (internal testing track) | 3 | P0 | 10-20 testers, feedback collection |
| S8.9 | Bug fixing & polish | 5 | P0 | Berdasarkan feedback beta |
| S8.10 | Release build + signing | 1 | P0 | App bundle, keystore setup |

### Deliverables
- [ ] Export/backup/restore working
- [ ] Settings screen complete
- [ ] Play Store assets ready
- [ ] Beta testing done
- [ ] App siap release

### Acceptance Criteria
- [ ] Export CSV → file bisa dibuka di Excel/Sheets
- [ ] Backup → file encrypted, tidak bisa dibaca tanpa PIN
- [ ] Restore → data kembali seperti saat backup
- [ ] Settings → semua konfigurasi accessible
- [ ] Beta feedback → semua critical bugs fixed
- [ ] APK size < 25MB
- [ ] Crash-free rate > 99.5%

---

## V1 LAUNCH CHECKLIST

### Technical
- [ ] Semua P0 features implemented dan tested
- [ ] No critical bugs
- [ ] Crash-free rate > 99.5%
- [ ] APK size < 25MB
- [ ] App launch time < 2 detik (cold start)
- [ ] Input transaction < 3 detik
- [ ] Room database migrations tested
- [ ] Biometric lock working di semua device
- [ ] Offline functionality verified (airplane mode test)

### UX
- [ ] Onboarding flow tested dengan user baru
- [ ] Empty states semua screen ada
- [ ] Error states semua ada
- [ ] Dark mode tested
- [ ] Haptic feedback consistent
- [ ] Animations smooth (no jank)
- [ ] Typography readable di semua screen size

---

## V2 SPRINT OVERVIEW (Month 4-6)

### Theme: "Makes you come back every day"

| Sprint | Focus | Key Features |
|--------|-------|--------------|
| V2-S1 | Split & Tags | Split transaction, tags system, notes & attachment |
| V2-S2 | Habit System | Weekly check-in, streak system (redesigned), progress feedback |
| V2-S3 | Anti Drop-off | Anti drop-off system, adaptive reminders |
| V2-S4 | Anti-Boncos | Daily spending allowance, anti-boncos logic |
| V2-S5 | Smart Categorization | Rule-based auto-suggest, learning from corrections |
| V2-S6 | Export Polish | PDF export, auto-backup reminder, backup scheduling |

---

## V3 SPRINT OVERVIEW (Month 7-9)

### Theme: "Becomes your financial companion"

| Sprint | Focus | Key Features |
|--------|-------|--------------|
| V3-S1 | Analytics | Cashflow trend, anomaly detection |
| V3-S2 | Goals | Goal system, progress tracking, estimasi |
| V3-S3 | Debt | Debt tracker, reminders, cicilan |
| V3-S4 | Widget | Home screen widget (small + medium) |
| V3-S5 | Polish | Advanced animations, custom themes |

---

## V4+ SPRINT OVERVIEW (Month 10+)

### Theme: "Hard to leave, impossible to replace"

| Sprint | Focus | Key Features |
|--------|-------|--------------|
| V4-S1 | Cloud Sync | Optional cloud sync, E2E encryption |
| V4-S2 | Multi-Currency | Multi-currency support, manual rate |
| V4-S3 | Investment | Investment tracking, net worth |
| V4-S4 | AI Insights | On-device ML insights (TensorFlow Lite) |
| V4-S5 | Social | Referral system, community features |

---

## DEVELOPMENT GUIDELINES

### Code Standards
- Kotlin official coding conventions
- Compose-first (no XML kecuali absolutely necessary)
- MVVM + Clean Architecture
- Repository pattern untuk data access
- Flow untuk reactive streams
- Coroutines untuk async operations
- Hilt untuk dependency injection

### Testing Strategy
- Unit tests untuk domain layer (use cases)
- Unit tests untuk repository (dengan fake data sources)
- UI tests untuk critical flows (input transaction, onboarding)
- Integration tests untuk Room DAO
- Manual testing untuk UX dan animations

### Performance Targets
- Cold start: < 2 detik
- Hot start: < 1 detik
- Transaction input: < 3 detik
- Dashboard load: < 1 detik
- APK size: < 25MB
- RAM usage: < 150MB
- Database size: < 10MB untuk 10.000 transaksi

### Git Workflow
- Main branch: production-ready
- Develop branch: integration
- Feature branches: `feature/xxx`
- Bug fix branches: `fix/xxx`
- PR required untuk merge ke develop
- Squash merge untuk clean history

### Release Cadence
- V1: 1 major release (launch)
- V2+: Bi-weekly minor releases
- Hotfix: As needed
- Beta: Continuous (internal testing track)

---

## RISK MITIGATION

| Risk | Impact | Probability | Mitigation |
|------|--------|-------------|------------|
| Scope creep | High | High | Strict P0/P1/P2 prioritization, no feature addition mid-sprint |
| Performance issues | High | Medium | Performance targets dari awal, profiling setiap sprint |
| Data loss | Critical | Low | Backup system, thorough testing, user warning |
| Biometric compatibility | Medium | Medium | Test di multiple devices, PIN fallback selalu ada |
| Room migration issues | High | Medium | Thorough migration testing, fallback strategy |
| User adoption low | High | Medium | Focus on UX, early beta testing, iterate based on feedback |
| Burnout (small team) | High | Medium | Realistic sprint planning, buffer time, no crunch |

---

## METRICS TO TRACK (Post-Launch)

### Acquisition
- Downloads per day
- Store conversion rate (view → install)
- Traffic sources

### Activation
- Onboarding completion rate
- First transaction within 24 hours
- Account setup completion

### Engagement
- DAU / MAU ratio
- Average sessions per day
- Average transactions per user per day
- Average session duration
- Feature usage (budget, templates, recurring)

### Retention
- Day 1, 7, 14, 30 retention
- Churn rate
- Reactivation rate

### Revenue (future)
- Premium conversion rate (kalau ada paid features)
- LTV per user

### Technical
- Crash-free rate
- ANR rate
- App launch time
- APK size trend

---

> **Sprint Planning Philosophy**: 
> - Plan for 80% capacity, leave 20% buffer untuk bug fixing dan unexpected issues
> - Setiap sprint harus deliver sesuatu yang bisa di-test oleh user
> - Jangan commit ke fitur yang tidak bisa selesai dalam 1 sprint — split lebih kecil
> - Quality > Speed. Better delay 1 minggu daripada release buggy feature.
> - User feedback > Developer assumption. Test early, test often.

