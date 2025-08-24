# Cloud Deployment Guide

## Masalah yang Diperbaiki

Jika Anda mengalami error `Communications link failure` atau `SQLNonTransientConnectionException` saat deploy di platform cloud, berikut adalah solusinya:

## Perubahan Konfigurasi

### 1. Docker Compose untuk Cloud

Gunakan file `docker-compose.cloud.yml` yang telah dioptimalkan untuk cloud deployment:

```bash
# Untuk deployment cloud, gunakan:
docker-compose -f docker-compose.cloud.yml up --build
```

### 2. Konfigurasi Database yang Dioptimalkan

- **Timeout yang lebih panjang**: MySQL health check dengan start_period 120s
- **Retry yang lebih banyak**: 20 retries untuk koneksi database
- **Connection pool yang dioptimalkan**: Pool size disesuaikan untuk cloud environment
- **Memory management**: JAVA_OPTS dengan Xmx512m untuk efisiensi memory

### 3. Health Check Endpoint

- Ditambahkan Spring Boot Actuator untuk monitoring
- Health check endpoint: `/api/actuator/health`
- Database health check otomatis

## Perbedaan Konfigurasi

### Local Development
```bash
docker-compose up --build
```

### Cloud Deployment
```bash
docker-compose -f docker-compose.cloud.yml up --build
```

## Troubleshooting

### Jika masih mengalami connection error:

1. **Pastikan platform cloud mendukung Docker Compose**
2. **Periksa resource limits** (CPU/Memory)
3. **Tunggu lebih lama** untuk MySQL startup (hingga 2 menit)
4. **Monitor logs**:
   ```bash
   docker-compose -f docker-compose.cloud.yml logs -f
   ```

### Environment Variables untuk Cloud Platform

Jika platform cloud Anda menggunakan environment variables terpisah:

```env
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/sewa_kamera_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&autoReconnect=true&failOverReadOnly=false&maxReconnects=30&connectTimeout=120000&socketTimeout=120000
SPRING_DATASOURCE_USERNAME=sewa_user
SPRING_DATASOURCE_PASSWORD=sewa_password
SPRING_PROFILES_ACTIVE=docker
JAVA_OPTS=-Xmx512m -Xms256m
```

## Monitoring

Setelah deployment berhasil, Anda dapat memantau aplikasi melalui:

- **Health Check**: `http://your-app-url/api/actuator/health`
- **Application Status**: Monitor logs untuk memastikan koneksi database berhasil

## Tips untuk Platform Cloud Tertentu

### Railway/Render/Heroku
- Gunakan `docker-compose.cloud.yml`
- Set environment variables melalui dashboard platform
- Monitor startup time (bisa memakan waktu 2-3 menit)

### DigitalOcean App Platform
- Gunakan App Spec dengan Docker Compose
- Set health check endpoint
- Pastikan resource allocation cukup

### AWS/GCP/Azure
- Gunakan container orchestration service
- Set proper networking configuration
- Monitor CloudWatch/Stackdriver logs