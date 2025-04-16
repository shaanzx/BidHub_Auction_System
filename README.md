# 🚀 Bid HUB - Online Auction System

<div align="center">
  <img src="https://github.com/user-attachments/assets/161642ce-c9bb-4918-983c-7b8a32ded83c" alt="Bid HUB Banner" width="80%">
</div>

 <!-- Replace with your actual banner image -->

# 💎 Bid HUB - Next-Gen Auction Platform

**Enterprise-grade auction system** with military-grade security and real-time bidding.  
Dual-dashboard architecture empowers **admins** with total control while giving **users** seamless auction experiences.  

✔ **Spring Boot** powered | ✔ **Bank-grade security** | ✔ **Real-time updates**  
✔ **Automated workflows** | ✔ **Mobile-responsive** | ✔ **Email notifications**

[![GitHub stars](https://img.shields.io/github/stars/shaanzx/BidHub_Auction_System?style=social)](https://github.com/shaanzx/BidHub_Auction_System/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/shaanzx/BidHub_Auction_System?style=social)](https://github.com/shaanzx/BidHub_Auction_System/network)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7%2B-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![License](https://img.shields.io/badge/License-MIT-green)

## ✨ Key Features

### 👨‍💻 Admin Dashboard
- **User Management** - Full CRUD operations for system users
- **Item Oversight** - Approve/reject items submitted by users
- **Category Control** - Manage auction categories and taxonomies
- **Bid Supervision** - Monitor all bidding activities in real-time
- **Payment Administration** - Process and verify transaction records
- **Analytics Dashboard** - View system statistics and performance metrics

### 👤 User Dashboard
- **Multi-item Listings** - Submit unlimited items for auction (pending admin approval)
- **Personalized Pricing** - Set your own starting bids and reserve prices
- **Bid Notifications** - Real-time updates on item bids
- **Auction Closure Alerts** - Email notifications for winning bids
- **Secure Transactions** - Protected payment processing
- **Bid History** - Track all your bidding activities

## 🛠️ Technology Stack

**Frontend**  
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=javascript&logoColor=black)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5.2+-7952B3?logo=bootstrap&logoColor=white)

**Backend**  
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7%2B-6DB33F?logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-5.7%2B-6DB33F?logo=springsecurity&logoColor=white)

**Database**  
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql&logoColor=white)

**DevOps**  
![Git](https://img.shields.io/badge/Git-F05032?logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?logo=github&logoColor=white)

## 📸 System Screenshots

### Admin Dashboard
| Feature | Preview |
|---------|---------|
| User Management | ![User Management](https://github.com/user-attachments/assets/1b2ab7a3-cf0f-416f-b4e4-46470e8a9ae1)
) |
| Item Approval | ![Item Approval](https://github.com/user-attachments/assets/3d3fb136-12ce-4c15-a947-104f9339a91a)
) |
| Bid Analytics | ![Bid Analytics](https://github.com/user-attachments/assets/7cb8463d-68aa-4636-a033-4e3f926a621b)
) |

### User Dashboard
| Feature | Preview |
|---------|---------|
| Dashboard View | ![DashBoard View](https://github.com/user-attachments/assets/770ad3e1-e68d-4206-811b-4ca28e870555)
) |
| Item Listing | ![Item Listing](https://github.com/user-attachments/assets/19a87d9a-8867-4f7d-a684-3ec82111fbfd)
) |
| Enter the bid | ![Enter the bid](https://github.com/user-attachments/assets/e0dd10f2-ed8a-4caf-80c4-02ea7781ead6)
) |
| User Add item form | ![User Add item form ](https://github.com/user-attachments/assets/9641717f-8929-438a-bc64-0678edcfaf33)
) |

## 🚀 Getting Started

### Prerequisites
- Java JDK 21+
- MySQL 8.0+
- Maven 3.8+
- Node.js (for frontend development)

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/shaanzx/BidHub_Auction_System.git
   cd BidHub_Auction_System
   ```
2. Set up database:
   ```sql
   CREATE DATABASE bidhubsystem;
   CREATE USER 'bidhub_user'@'root' IDENTIFIED BY 'securepassword';
   GRANT ALL PRIVILEGES ON bidhub.* TO 'bidhub_user'@'localhost';
   FLUSH PRIVILEGES;
   ```
3. Configure application:
   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```
4. Build and run:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```


## 🔒 Security Features
- Role-based access control (RBAC)
- Password encryption with BCrypt
- CSRF protection
- Session management with timeout
- Secure HTTP headers (CSP, XSS Protection)
- Audit logging for sensitive operations


## 📄 License
Distributed under the MIT License. See `LICENSE` for more information.

## 📧 Contact
**Shan** - [shaanz11.11@gmail.com](mailto:shaanz11.11@gmail.com)  
**GitHub**: [https://github.com/shaanzx](https://github.com/shaanzx)  
**Project Link**: [https://github.com/shaanzx/BidHub_Auction_System](https://github.com/shaanzx/BidHub_Auction_System)

---

<div align="center">
  <sub>Built with ❤️ by Shan | © 2025 Bid HUB</sub>
</div>
