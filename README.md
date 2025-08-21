# 🎫 Helpdesk Sistemi

Modern ve kullanıcı dostu bir destek talebi yönetim sistemi. Spring Boot backend ve vanilla JavaScript frontend ile geliştirilmiştir.

## 📋 Proje Açıklaması

Helpdesk Sistemi, kullanıcıların destek taleplerini oluşturmasına, takip etmesine ve yöneticilerin bu talepleri yönetmesine olanak sağlayan fullstack bir web uygulamasıdır.

### ✨ Özellikler

- **🔐 Kullanıcı Yönetimi**: JWT tabanlı kimlik doğrulama ve yetkilendirme
- **🎫 Ticket Yönetimi**: Destek taleplerinin oluşturulması, düzenlenmesi ve takibi
- **🔍 Gelişmiş Arama**: Durum, öncelik ve anahtar kelime bazlı filtreleme
- **👥 Rol Tabanlı Erişim**: Admin ve User rolleri ile farklı yetki seviyeleri
- **📊 Pagination**: Büyük veri setleri için sayfalama desteği

## 🏗️ Teknoloji Stack

### Backend
- **Java 21** - Modern Java sürümü
- **Spring Boot 3.5.4** - Ana framework
- **Spring Security** - Güvenlik ve kimlik doğrulama
- **Spring Data JPA** - Veritabanı işlemleri
- **PostgreSQL** - Ana veritabanı
- **JWT** - Token tabanlı kimlik doğrulama
- **Lombok** - Boilerplate kod azaltma

### Frontend
- **Vanilla JavaScript** - Modern ES6+ özellikleri
- **HTML5 & CSS3** - Responsive tasarım
- **CSS Grid & Flexbox** - Modern layout sistemleri

### DevOps
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration

## 🚀 Kurulum ve Çalıştırma

### Gereksinimler
- Java 21+
- Docker & Docker Compose
- Maven (opsiyonel)

### Hızlı Başlangıç

1. **Projeyi klonlayın**
```bash
git clone https://github.com/hakancankul/helpdesk
cd helpdesk
```

2. **Docker ile çalıştırın**
```bash
docker-compose up -d
```

3. **Uygulamaya erişin**
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080
- PostgreSQL: localhost:5432

### Manuel Kurulum

1. **Veritabanını başlatın**
```bash
docker run -d \
  --name helpdesk-postgres \
  -e POSTGRES_DB=helpdesk \
  -e POSTGRES_USER=helpdesk_user \
  -e POSTGRES_PASSWORD=helpdesk_password \
  -p 5432:5432 \
  postgres:15-alpine
```

2. **Backend'i çalıştırın**
```bash
cd backend
./mvnw spring-boot:run
```

3. **Frontend'i açın**
```bash
cd frontend
# index.html dosyasını tarayıcıda açın
```

## 👥 Seed Kullanıcılar

Sistem ilk çalıştırıldığında otomatik olarak aşağıdaki test kullanıcıları oluşturulur:

### Admin Kullanıcı
- **Kullanıcı Adı**: `admin`
- **Şifre**: `admin123`
- **Rol**: ADMIN
- **Yetkiler**: Tüm ticket'ları görüntüleme, düzenleme ve silme

### Test Kullanıcıları
- **Kullanıcı Adları**: `user1`, `user2`, `user3`, `user4`, `user5`
- **Şifre**: `1234`
- **Rol**: USER
- **Yetkiler**: Kendi ticket'larını oluşturma, düzenleme ve silme

### Örnek Veriler
- **15 adet örnek ticket** otomatik oluşturulur
- Farklı durumlar (Açık, İşlemde, Çözüldü, Kapalı)
- Farklı öncelikler (Düşük, Orta, Yüksek)
- Gerçekçi başlık ve açıklamalar

## 🔧 API Endpoints

### Kimlik Doğrulama
- `POST /api/auth/register` - Yeni kullanıcı kaydı
- `POST /api/auth/login` - Kullanıcı girişi

### Ticket Yönetimi
- `GET /api/tickets` - Tüm ticket'ları listele (Admin)
- `GET /api/tickets/{id}` - Ticket detayı
- `POST /api/tickets` - Yeni ticket oluştur
- `PUT /api/tickets/{id}/user-update` - User ticket güncelleme
- `PUT /api/tickets/{id}/admin-update` - Admin ticket güncelleme
- `DELETE /api/tickets/{id}` - Ticket sil
- `GET /api/tickets/search` - Ticket arama ve filtreleme


## 📁 Proje Yapısı

```
helpdesk/
├── backend/                 # Spring Boot uygulaması
│   ├── src/main/java/
│   │   └── com/hakan/helpdesk/
│   │       ├── config/      # Konfigürasyon sınıfları
│   │       ├── controller/  # REST API controller'ları
│   │       ├── dto/         # Data Transfer Objects
│   │       ├── model/       # JPA entity'leri
│   │       ├── repository/  # Veritabanı repository'leri
│   │       ├── security/    # Güvenlik sınıfları
│   │       └── service/     # İş mantığı servisleri
│   └── pom.xml
├── frontend/                # HTML/CSS/JS dosyaları
│   ├── index.html          # Ana uygulama
│   └── Dockerfile
├── docker-compose.yml       # Docker orchestration
└── README.md
```



### Veritabanı Şeması
- **users**: Kullanıcı bilgileri
- **roles**: Kullanıcı rolleri
- **tickets**: Destek talepleri
- **user_roles**: Kullanıcı-rol ilişkileri

```mermaid
erDiagram
    USERS {
        bigint id PK
        varchar username
        varchar password
        timestamp created_at
        timestamp updated_at
    }

    ROLES {
        bigint id PK
        varchar name
    }

    USER_ROLES {
        bigint user_id FK
        bigint role_id FK
    }

    TICKETS {
        bigint id PK
        varchar title
        text description
        varchar status
        varchar priority
        bigint created_by_id FK
        bigint assigned_to_id FK
        timestamp created_at
        timestamp updated_at
    }

    USERS ||--o{ USER_ROLES : "has roles"
    ROLES ||--o{ USER_ROLES : "belongs to"
    USERS ||--o{ TICKETS : "created tickets"
    USERS ||--o{ TICKETS : "assigned tickets"


**Not**: Bu proje Spring Boot 3.x ve Java 21 kullanmaktadır. Eski sürümlerle uyumluluk için gerekli değişiklikleri yapmanız gerekebilir.
