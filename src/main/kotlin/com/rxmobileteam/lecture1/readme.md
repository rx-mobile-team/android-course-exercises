Improve kiến thức OOP và thấy được sức mạnh của tính đa hình bằng cách đảo ngược compile-time dependency 💪

### Nhiệm vụ

`ProductService` và package `product` đại diện cho một thành phần của hệ thống chứa business logic. `ProductDao`
và package `data` đại diện cho một thành phần của hệ thống triển khai `Data Access Layer`. Nhiệm vụ của bạn là
triển khai phần *TODO* của các lớp đó **theo các nguyên tắc thiết kế OOP.**

### Mục tiêu

* **triển khai `ProductDao`** (data access object) bằng `HashMap` ✅
* **triển khai service method** cho phép **thêm một product** sử dụng DAO ✅
* **triển khai service method** cho phép **tìm kiếm product** sử dụng DAO ✅
* **đảo ngược sự phụ thuộc** giữa service and DAO bằng việc thêm một interface ✅

### Kiến thức liên quan ℹ️

* [SOLID](https://en.wikipedia.org/wiki/SOLID)
