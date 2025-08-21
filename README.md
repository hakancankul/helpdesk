# 🎫 Helpdesk Sistemi

Bu proje, Spring Boot backend ve minimal HTML/CSS/JavaScript frontend ile geliştirilmiş bir destek talebi yönetim sistemidir.

## 🏗️ Proje Yapısı

```
helpdesk/
├── backend/                 # Spring Boot Backend
│   ├── src/main/java/
│   │   └── com/hakan/helpdesk/
│   │       ├── controller/ # REST API Controllers
│   │       ├── service/    # Business Logic
│   │       ├── repository/ # Data Access Layer
│   │       ├── model/      # Entity Classes
│   │       ├── dto/        # Data Transfer Objects
│   │       ├── security/   # Security Configuration
│   │       └── config/     # Application Configuration
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
├── frontend/               # HTML Frontend
│   └── index.html
└── README.md
```

## 🚀 Özellikler

### Backend (Spring Boot)
- **Spring Security + JWT** - Güvenli kimlik doğrulama
- **Spring Data JPA** - Veritabanı işlemleri
- **PostgreSQL** - Ana veritabanı
- **Role-based Access Control** - USER ve ADMIN rolleri
- **RESTful API** - Modern web servisleri

### Frontend (HTML/CSS/JavaScript)
- **Responsive Design** - Mobil uyumlu arayüz
- **Modern UI/UX** - Gradient tasarım ve animasyonlar
- **Real-time Updates** - Anlık veri güncellemeleri
- **Search & Filter** - Gelişmiş arama özellikleri

## 🛠️ Teknolojiler

### Backend
- Java 21
- Spring Boot 3.5.4
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT (JSON Web Token)
- Lombok
- Maven

### Frontend
- HTML5
- CSS3 (Grid, Flexbox, Animations)
- Vanilla JavaScript (ES6+)
- Fetch API
- Responsive Design

## 📋 Sistem Gereksinimleri

- Java 21+
- Maven 3.6+
- PostgreSQL 12+
- Modern web browser

## 🚀 Kurulum ve Çalıştırma

### 1. Backend Kurulumu

```bash
# PostgreSQL veritabanını başlat
# application.properties'deki veritabanı bilgilerini güncelle

cd backend
mvn clean install
mvn spring-boot:run
```

Backend `http://localhost:8080` adresinde çalışacak.

### 2. Frontend Kurulumu

```bash
# Frontend klasörüne git
cd frontend

# index.html dosyasını herhangi bir modern web tarayıcısında aç
# Veya basit bir HTTP server başlat:
python -m http.server 8000
# Sonra http://localhost:8000 adresine git
```

## 🔐 API Endpoints

### Authentication
- `POST /api/auth/register` - Kullanıcı kaydı
- `POST /api/auth/login` - Kullanıcı girişi

### Tickets
- `POST /api/tickets` - Yeni ticket oluştur
- `GET /api/tickets` - Tüm ticket'ları listele (ADMIN)
- `GET /api/tickets/{id}` - Ticket detayını getir
- `PUT /api/tickets/{id}/user-update` - USER ticket güncelleme
- `PUT /api/tickets/{id}/admin-update` - ADMIN ticket güncelleme
- `DELETE /api/tickets/{id}` - Ticket sil
- `GET /api/tickets/search` - Ticket arama ve filtreleme

## 🎯 Kullanım Senaryoları

### 1. Kullanıcı Kaydı ve Girişi
- Yeni kullanıcı kaydı
- JWT token ile güvenli giriş
- Oturum yönetimi

### 2. Destek Talebi Yönetimi
- Yeni destek talebi oluşturma
- Öncelik seviyesi belirleme (LOW, MEDIUM, HIGH)
- Durum takibi (OPEN, IN_PROGRESS, RESOLVED, CLOSED)
- Açıklama ekleme

### 3. Arama ve Filtreleme
- Durum bazında filtreleme
- Öncelik bazında filtreleme
- Anahtar kelime ile arama
- Sayfalama desteği

### 4. Güvenlik
- Role-based access control
- JWT token authentication
- API endpoint koruması

## 🎨 Frontend Özellikleri

- **Modern Tasarım**: Gradient arka plan, gölgeler, yuvarlatılmış köşeler
- **Responsive Layout**: Grid ve Flexbox ile esnek düzen
- **Interactive Elements**: Hover efektleri, animasyonlar
- **Status Badges**: Renkli durum ve öncelik göstergeleri
- **Form Validation**: HTML5 validation ve custom error handling
- **Real-time Feedback**: Başarı/hata mesajları

## 🔧 Konfigürasyon

### Backend Konfigürasyonu
`backend/src/main/resources/application.properties`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/helpdesk
spring.datasource.username=postgres
spring.datasource.password=1234

# JWT
jwt.secret=your-secret-key

# Server
server.port=8080
```

### Frontend Konfigürasyonu
`frontend/index.html` içinde API base URL'i güncelleyin:

```javascript
const API_BASE = 'http://localhost:8080/api';
```

## 🧪 Test

### Backend Test
```bash
cd backend
mvn test
```

### Frontend Test
- Modern web tarayıcısında `index.html` dosyasını açın
- Farklı ekran boyutlarında responsive tasarımı test edin
- Tüm form validasyonlarını test edin

## 📱 Responsive Design

Frontend aşağıdaki ekran boyutları için optimize edilmiştir:
- **Desktop**: 1200px+
- **Tablet**: 768px - 1199px
- **Mobile**: 320px - 767px

## 🔒 Güvenlik Özellikleri

- JWT token tabanlı kimlik doğrulama
- Role-based access control
- API endpoint koruması
- Güvenli şifre hashleme
- CORS konfigürasyonu

## 🚀 Gelecek Geliştirmeler

- [ ] Real-time notifications (WebSocket)
- [ ] File upload desteği
- [ ] Email notifications
- [ ] Advanced reporting
- [ ] Mobile app (React Native)
- [ ] Dark mode
- [ ] Multi-language support

## 🤝 Katkıda Bulunma

1. Fork yapın
2. Feature branch oluşturun (`git checkout -b feature/amazing-feature`)
3. Commit yapın (`git commit -m 'Add amazing feature'`)
4. Push yapın (`git push origin feature/amazing-feature`)
5. Pull Request oluşturun

## 📄 Lisans

Bu proje MIT lisansı altında lisanslanmıştır.

## 👨‍💻 Geliştirici

**Hakan** - Backend ve Frontend geliştirici

---

**Not**: Bu proje eğitim amaçlı geliştirilmiştir. Production ortamında kullanmadan önce güvenlik testleri yapılması önerilir.
