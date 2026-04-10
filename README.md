Mie Gacoan POS API
Description

REST API untuk mengelola produk dan transaksi penjualan dengan sistem manajemen stok otomatis.

Features
- Product CRUD (Create, Read, Update, Delete)
- Sales Transaction (multi-item)
- Automatic stock update
- Validation & Exception Handling
- Caching (product list)
- Logging
- Authentication & Authorize

## Database Design

![ERD](/MIE_ERD.png)

ERD ini menggambarkan relasi antara Product, Sale, dan SaleItem dalam sistem transaksi penjualan.

- Product menyimpan data produk dan stok
- Sale merepresentasikan transaksi
- SaleItem menyimpan detail produk dalam setiap transaksi

Relasi:
- Satu Product dapat muncul di banyak SaleItem (1:N)
- Satu Sale memiliki banyak SaleItem (1:N)

Struktur ini memungkinkan sistem menangani transaksi dengan banyak produk sekaligus serta menjaga konsistensi stok.

Tech Stack
- Java Spring Boot
- Spring Data JPA
- MySQL
- Swagger (OpenAPI)
- Lombok

  API Endpoints
  
Product 
- POST /products
- GET /products
- GET /products/{id}
- PUT /products/{id}
- DELETE /products/{id} 

Sales
- POST /sales 

Example Request 
    
{
   
     "items": [{ "productId": 1, "quantity": 2 }]

}

How to Run

1. Clone repository
2. Setup database:
3. CREATE DATABASE mie_gacoan;
4. Run application
5. Open Swagger: http://localhost:8080/swagger-ui/index.html

Key Concept
- Stock hanya berubah melalui transaksi
- Menggunakan @Transactional untuk menjaga konsistensi data
- Menggunakan cache untuk optimasi performa