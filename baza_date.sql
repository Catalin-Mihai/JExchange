-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Gazdă: 127.0.0.1
-- Timp de generare: mai 24, 2020 la 08:29 PM
-- Versiune server: 10.1.37-MariaDB
-- Versiune PHP: 7.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Bază de date: `pao`
--

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `clients`
--

CREATE TABLE `clients` (
  `id` int(11) NOT NULL,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Eliminarea datelor din tabel `clients`
--

INSERT INTO `clients` (`id`, `firstname`, `lastname`) VALUES
(1, 'Catalin', 'Cioboata'),
(2, 'Cal', 'Banana'),
(3, 'Andreea', 'Busoi'),
(5, 'Vlad', 'Andrei');

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `clients_money`
--

CREATE TABLE `clients_money` (
  `id` int(11) NOT NULL,
  `currency_id` int(11) DEFAULT NULL,
  `client_id` int(11) DEFAULT NULL,
  `amount` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Eliminarea datelor din tabel `clients_money`
--

INSERT INTO `clients_money` (`id`, `currency_id`, `client_id`, `amount`) VALUES
(1, 1, 1, 797),
(2, 2, 3, 200),
(3, 1, 3, 3000),
(4, 2, 3, 500),
(7, 2, 5, 500),
(9, 6, 1, 0),
(10, 2, 1, 1000),
(11, 10, 1, 216.63);

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `currencies`
--

CREATE TABLE `currencies` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `symbol` varchar(50) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Eliminarea datelor din tabel `currencies`
--

INSERT INTO `currencies` (`id`, `name`, `symbol`, `country`) VALUES
(1, 'Leu', 'RON', 'Romania'),
(2, 'Euro', 'EUR', 'Europa'),
(6, 'Lira', 'L', NULL),
(7, 'Dolar', 'USD', 'SUA'),
(10, 'Forint', 'F', 'Ungaria');

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `exchanged_money`
--

CREATE TABLE `exchanged_money` (
  `id` int(11) NOT NULL,
  `currency_id` int(11) DEFAULT NULL,
  `amount` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Eliminarea datelor din tabel `exchanged_money`
--

INSERT INTO `exchanged_money` (`id`, `currency_id`, `amount`) VALUES
(1, 6, 11.541),
(2, 1, 60.0132),
(3, 6, 200),
(4, 2, 240),
(5, 6, 50),
(6, 2, 60),
(7, 6, 10),
(8, 2, 12),
(9, 6, 40),
(10, 2, 48),
(11, 1, 0),
(12, 10, 0),
(13, 1, 3),
(14, 10, 216.63);

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `exchanges`
--

CREATE TABLE `exchanges` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL DEFAULT '0',
  `money_given_id` int(11) DEFAULT NULL,
  `money_received_id` int(11) DEFAULT NULL,
  `office_id` int(11) DEFAULT NULL,
  `exchange_rate_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Eliminarea datelor din tabel `exchanges`
--

INSERT INTO `exchanges` (`id`, `client_id`, `money_given_id`, `money_received_id`, `office_id`, `exchange_rate_id`) VALUES
(1, 1, 1, 2, 3, 4),
(2, 1, 3, 4, 3, 6),
(3, 1, 5, 6, 3, 6),
(4, 1, 7, 8, 3, 6),
(5, 1, 9, 10, 3, 6),
(6, 1, 11, 12, 4, 8),
(7, 1, 13, 14, 4, 8);

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `exchange_rates`
--

CREATE TABLE `exchange_rates` (
  `id` int(11) NOT NULL,
  `from_currency_id` int(11) DEFAULT NULL,
  `to_currency_id` int(11) DEFAULT NULL,
  `rate` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Eliminarea datelor din tabel `exchange_rates`
--

INSERT INTO `exchange_rates` (`id`, `from_currency_id`, `to_currency_id`, `rate`) VALUES
(1, 2, 1, 4.8),
(2, 7, 1, 4.1),
(4, 6, 1, 5.2),
(5, 1, 6, 0.192308),
(6, 6, 2, 1.2),
(7, 2, 6, 0.833333),
(8, 1, 10, 72.21),
(9, 10, 1, 0.0138485);

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `offices`
--

CREATE TABLE `offices` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Eliminarea datelor din tabel `offices`
--

INSERT INTO `offices` (`id`, `name`, `address`) VALUES
(1, 'Casa1', 'Strada Nicolaie Balcescu, Pitesti'),
(2, 'Casa2', 'Strada Ion Creanga'),
(3, 'Casa3', 'Strada Brancusi'),
(4, 'Casa4', 'Strada Ghioceilor');

-- --------------------------------------------------------

--
-- Structură tabel pentru tabel `office_money`
--

CREATE TABLE `office_money` (
  `id` int(11) NOT NULL,
  `currency_id` int(11) DEFAULT NULL,
  `office_id` int(11) DEFAULT NULL,
  `amount` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Eliminarea datelor din tabel `office_money`
--

INSERT INTO `office_money` (`id`, `currency_id`, `office_id`, `amount`) VALUES
(1, 1, 1, 2000),
(2, 2, 2, 3300),
(4, 2, 3, 140),
(6, 6, 3, 600.001),
(9, 1, 3, 839.982),
(10, 10, 4, 83.37),
(11, 1, 4, 3);

--
-- Indexuri pentru tabele eliminate
--

--
-- Indexuri pentru tabele `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`id`);

--
-- Indexuri pentru tabele `clients_money`
--
ALTER TABLE `clients_money`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_clients_money_client_id` (`client_id`),
  ADD KEY `FK_clients_money_currency_id` (`currency_id`);

--
-- Indexuri pentru tabele `currencies`
--
ALTER TABLE `currencies`
  ADD PRIMARY KEY (`id`);

--
-- Indexuri pentru tabele `exchanged_money`
--
ALTER TABLE `exchanged_money`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_exchanged_currency_id` (`currency_id`);

--
-- Indexuri pentru tabele `exchanges`
--
ALTER TABLE `exchanges`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_client_id` (`client_id`),
  ADD KEY `FK_office_id` (`office_id`),
  ADD KEY `FK_exchange_rate_id` (`exchange_rate_id`),
  ADD KEY `FK_exchanges_pao.exchanged_money_2` (`money_given_id`),
  ADD KEY `FK_exchanges_pao.exchanged_money` (`money_received_id`);

--
-- Indexuri pentru tabele `exchange_rates`
--
ALTER TABLE `exchange_rates`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_from_currency_id` (`from_currency_id`),
  ADD KEY `FK_to_currency_id` (`to_currency_id`);

--
-- Indexuri pentru tabele `offices`
--
ALTER TABLE `offices`
  ADD PRIMARY KEY (`id`);

--
-- Indexuri pentru tabele `office_money`
--
ALTER TABLE `office_money`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_office_money_currencies` (`currency_id`),
  ADD KEY `FK_office_money_offices` (`office_id`);

--
-- AUTO_INCREMENT pentru tabele eliminate
--

--
-- AUTO_INCREMENT pentru tabele `clients`
--
ALTER TABLE `clients`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pentru tabele `clients_money`
--
ALTER TABLE `clients_money`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT pentru tabele `currencies`
--
ALTER TABLE `currencies`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pentru tabele `exchanged_money`
--
ALTER TABLE `exchanged_money`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT pentru tabele `exchanges`
--
ALTER TABLE `exchanges`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pentru tabele `exchange_rates`
--
ALTER TABLE `exchange_rates`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pentru tabele `offices`
--
ALTER TABLE `offices`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pentru tabele `office_money`
--
ALTER TABLE `office_money`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constrângeri pentru tabele eliminate
--

--
-- Constrângeri pentru tabele `clients_money`
--
ALTER TABLE `clients_money`
  ADD CONSTRAINT `FK_clients_money_client_id` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`),
  ADD CONSTRAINT `FK_clients_money_currency_id` FOREIGN KEY (`currency_id`) REFERENCES `currencies` (`id`);

--
-- Constrângeri pentru tabele `exchanged_money`
--
ALTER TABLE `exchanged_money`
  ADD CONSTRAINT `FK_exchanged_currency_id` FOREIGN KEY (`currency_id`) REFERENCES `currencies` (`id`);

--
-- Constrângeri pentru tabele `exchanges`
--
ALTER TABLE `exchanges`
  ADD CONSTRAINT `FK_client_id` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`),
  ADD CONSTRAINT `FK_exchange_rate_id` FOREIGN KEY (`exchange_rate_id`) REFERENCES `exchange_rates` (`id`),
  ADD CONSTRAINT `FK_exchanges_pao.exchanged_money` FOREIGN KEY (`money_received_id`) REFERENCES `exchanged_money` (`id`),
  ADD CONSTRAINT `FK_exchanges_pao.exchanged_money_2` FOREIGN KEY (`money_given_id`) REFERENCES `exchanged_money` (`id`),
  ADD CONSTRAINT `FK_office_id` FOREIGN KEY (`office_id`) REFERENCES `offices` (`id`);

--
-- Constrângeri pentru tabele `exchange_rates`
--
ALTER TABLE `exchange_rates`
  ADD CONSTRAINT `FK_from_currency_id` FOREIGN KEY (`from_currency_id`) REFERENCES `currencies` (`id`),
  ADD CONSTRAINT `FK_to_currency_id` FOREIGN KEY (`to_currency_id`) REFERENCES `currencies` (`id`);

--
-- Constrângeri pentru tabele `office_money`
--
ALTER TABLE `office_money`
  ADD CONSTRAINT `FK_office_money_currencies` FOREIGN KEY (`currency_id`) REFERENCES `currencies` (`id`),
  ADD CONSTRAINT `FK_office_money_offices` FOREIGN KEY (`office_id`) REFERENCES `offices` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
