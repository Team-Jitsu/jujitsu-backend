CREATE TABLE `users` (
                         `user_id` bigint NOT NULL AUTO_INCREMENT,
                         `password` varchar(255) NOT NULL,
                         `email` varchar(100) NOT NULL UNIQUE,
                         `role` enum('선수', '수련생', '관리자') NOT NULL,
                         `age` int NOT NULL,
                         `sex` enum('male', 'female') NOT NULL,
                         `nickname` varchar(100) DEFAULT NULL,
                         `region` varchar(100) NOT NULL,
                         `is_active` tinyint(1) NOT NULL,
                         `created_at` datetime NOT NULL,
                         `mobile_number` varchar(30) NOT NULL,
                         `gym_location` varchar(100) DEFAULT NULL,
                         PRIMARY KEY (`user_id`),
                         INDEX (`is_active`)
);

CREATE TABLE `admin_history` (
                                 `admin_history_id` bigint NOT NULL AUTO_INCREMENT,
                                 `user_id` bigint NOT NULL,
                                 `activity_detail` varchar(100) NOT NULL,
                                 `created_at` datetime NOT NULL,
                                 PRIMARY KEY (`admin_history_id`),
                                 INDEX (`created_at`),
                                 FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE `trainers` (
                            `trainer_id` bigint NOT NULL AUTO_INCREMENT,
                            `user_id` bigint NOT NULL,
                            `account_owner_name` varchar(100) NOT NULL,
                            `account_number` varchar(100) NOT NULL,
                            `bio` text DEFAULT NULL,
                            `automatic_settlement` tinyint(1) NOT NULL DEFAULT 0,
                            PRIMARY KEY (`trainer_id`),
                            FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE `earnings` (
                            `earning_id` bigint NOT NULL AUTO_INCREMENT,
                            `trainer_id` bigint NOT NULL,
                            `total_amount` bigint NOT NULL,
                            `is_settled` tinyint(1) NOT NULL DEFAULT 0,
                            `request_settlement` tinyint(1) NOT NULL DEFAULT 0,
                            `complete_settlement_at` datetime DEFAULT NULL,
                            `request_settlement_at` datetime DEFAULT NULL,
                            PRIMARY KEY (`earning_id`),
                            INDEX `idx_settlement` (`is_settled`, `request_settlement`),
                            INDEX (`complete_settlement_at`),
                            INDEX (`request_settlement_at`),
                            FOREIGN KEY (`trainer_id`) REFERENCES `trainers`(`trainer_id`)
);

CREATE TABLE `earning_buffer` (
                                  `buffer_id` bigint NOT NULL AUTO_INCREMENT,
                                  `trainer_id` bigint NOT NULL,
                                  `user_videos_id` bigint NOT NULL,
                                  `earning_id` bigint DEFAULT NULL,
                                  `amount` int NOT NULL,
                                  `created_at` datetime NOT NULL,
                                  PRIMARY KEY (`buffer_id`),
                                  INDEX `idx_earning` (`trainer_id`, `earning_id`),
                                  FOREIGN KEY (`trainer_id`) REFERENCES `trainers`(`trainer_id`)
                                      FOREIGN KEY (`user_videos_id`) REFERENCES `user_videos`(`user_videos_id`),
                                  FOREIGN KEY (`earning_id`) REFERENCES `earnings`(`earning_id`)
);

CREATE TABLE `videos` (
                          `video_id` bigint NOT NULL AUTO_INCREMENT,
                          `trainer_id` bigint NOT NULL,
                          `title` varchar(100) NOT NULL,
                          `url` varchar(200) NOT NULL,
                          `description` text DEFAULT NULL,
                          `upload_time` datetime NOT NULL,
                          `price` int NOT NULL,
                          `likes_count` int NOT NULL DEFAULT 0,
                          PRIMARY KEY (`video_id`),
                          INDEX (`upload_time`),
                          FOREIGN KEY (`trainer_id`) REFERENCES `trainers`(`trainer_id`)
);

CREATE TABLE `categories` (
                              `category_id` bigint NOT NULL AUTO_INCREMENT,
                              `category_name` varchar(200) NOT NULL UNIQUE,
                              PRIMARY KEY (`category_id`)
);

CREATE TABLE `user_videos` (
                               `user_videos_id` bigint NOT NULL AUTO_INCREMENT,
                               `user_id` bigint NOT NULL,
                               `video_id` bigint NOT NULL,
                               `purchase_price` int NOT NULL,
                               `purchased_at` datetime NOT NULL,
                               PRIMARY KEY (`user_videos_id`),
                               INDEX (`purchased_at`),
                               FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
                               FOREIGN KEY (`video_id`) REFERENCES `videos`(`video_id`)
);

CREATE TABLE `orders` (
                          `order_id`	BIGINT	NOT NULL	COMMENT 'autoincrement',
                          `toss_order_id`	VARCHAR(100)	NOT NULL	COMMENT 'UUID',
                          `amount`	int	NOT NULL,
                          `status`	enum	NOT NULL,
                          `payment_key`	VARCHAR(200)	NULL	COMMENT '결제 완료시 받는 key',
                          `created_at`	DATETIME	NOT NULL,
                          `user_id`	bigint	NOT NULL	COMMENT 'autoincrement',
                          `user_videos_id`	bigint	NULL	COMMENT 'autoincrement'
                          PRIMARY KEY (`user_videos_id`),
                          INDEX (`order_id`),
                          FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
                          FOREIGN KEY (`user_videos_id`) REFERENCES `user_videos`(`user_videos_id`)
);

CREATE TABLE `video_categories` (
                                    `video_id` bigint NOT NULL,
                                    `category_id` bigint NOT NULL,
                                    PRIMARY KEY (`video_id`, `category_id`),
                                    FOREIGN KEY (`video_id`) REFERENCES `videos`(`video_id`),
                                    FOREIGN KEY (`category_id`) REFERENCES `categories`(`category_id`)
);

CREATE TABLE `specialty` (
                             `specialty_id` bigint NOT NULL AUTO_INCREMENT,
                             `specialty_name` varchar(100) NOT NULL UNIQUE,
                             PRIMARY KEY (`specialty_id`)
);

CREATE TABLE `trainer_specialty` (
                                     `specialty_id` bigint NOT NULL,
                                     `trainer_id` bigint NOT NULL,
                                     PRIMARY KEY (`specialty_id`, `trainer_id`),
                                     FOREIGN KEY (`specialty_id`) REFERENCES `specialty`(`specialty_id`),
                                     FOREIGN KEY (`trainer_id`) REFERENCES `trainers`(`trainer_id`)
);

-- specialty 테이블 데이터 삽입
INSERT INTO `specialty` (`specialty_name`) VALUES
                                               ('주짓수(기)'),
                                               ('주짓수(노기)'),
                                               ('유도'),
                                               ('레슬링'),
                                               ('삼보'),
                                               ('쿠도'),
                                               ('MMA(종합격투기)'),
                                               ('복싱'),
                                               ('무에타이'),
                                               ('킥복싱'),
                                               ('태권도');

-- categories 테이블 데이터 삽입
INSERT INTO `categories` (`category_name`) VALUES
                                               ('노기 주짓수'),
                                               ('도복 주짓수'),
                                               ('레슬링'),
                                               ('유도'),
                                               ('MMA'),
                                               ('복싱'),
                                               ('무에타이'),
                                               ('킥복싱'),
                                               ('태권도'),
                                               ('삼보'),
                                               ('쿠도');
