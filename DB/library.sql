-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 12 sep. 2023 à 17:33
-- Version du serveur : 10.4.25-MariaDB
-- Version de PHP : 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `library`
--

-- --------------------------------------------------------

--
-- Structure de la table `bibliothécaire`
--

CREATE TABLE `bibliothécaire` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `cin` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id`, `name`, `cin`) VALUES
(1, 'karim', 'D13432'),
(2, 'boura', 'D543212'),
(3, 'karim', 'mahjour'),
(4, 'ahmed', 'F543'),
(5, 'ahmed', 'G543'),
(6, 'ahmed', '5435'),
(7, 'zrefzer', '54335'),
(8, 'zdezde', '543'),
(9, 'ouss', 'H5432'),
(10, 'jhvjhvjhv', '345678'),
(11, 'KARIM mahjour', 'K343434'),
(12, 'servoy', 'A6543'),
(13, 'servoy', 'A5432'),
(14, 'servoy', 'A65432'),
(15, 'karimabd', 'DC6543'),
(16, 'b3rda', 'E5432'),
(17, 'JHVKJHV', '345678'),
(19, 'KARIM', 'd'),
(20, 'mohamed b3rda', 'D6543'),
(21, 'fouad', 'D6543');

-- --------------------------------------------------------

--
-- Structure de la table `empruntés-livres`
--

CREATE TABLE `empruntés-livres` (
  `id` int(11) NOT NULL,
  `id-livre` int(11) NOT NULL,
  `id-d'emprunteur` int(11) NOT NULL,
  `date-de-prise` date NOT NULL,
  `date-de-retour` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `empruntés-livres`
--

INSERT INTO `empruntés-livres` (`id`, `id-livre`, `id-d'emprunteur`, `date-de-prise`, `date-de-retour`) VALUES
(18, 5, 15, '2023-09-06', '2023-09-14'),
(19, 2, 4, '2023-09-07', '2023-07-09'),
(22, 10, 10, '2023-09-11', '2023-09-18'),
(23, 12, 9, '2023-09-11', '2023-05-07');

--
-- Déclencheurs `empruntés-livres`
--
DELIMITER $$
CREATE TRIGGER `UpdateBookStatusToDisponibleWhenDelete` AFTER DELETE ON `empruntés-livres` FOR EACH ROW BEGIN
    UPDATE `livre` SET `status` = 'disponible' WHERE `id` = OLD.`id-livre` ;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `livre`
--

CREATE TABLE `livre` (
  `id` int(11) NOT NULL,
  `titre` varchar(100) NOT NULL,
  `auteur` varchar(30) NOT NULL,
  `ISBN` int(13) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `livre`
--

INSERT INTO `livre` (`id`, `titre`, `auteur`, `ISBN`, `status`) VALUES
(2, 'quran', 'mohamed bourra', 555, 'perdu'),
(5, 'aaa', 'ccc', 222, 'emprunté'),
(8, 'title', 'autgfd', 1212121212, 'disponible'),
(10, 'tlt', '777', 7777, 'emprunté'),
(12, 'rrr', 'fff', 150, 'perdu'),
(13, 'tlt', 'karim', 90, 'disponible');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `bibliothécaire`
--
ALTER TABLE `bibliothécaire`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `empruntés-livres`
--
ALTER TABLE `empruntés-livres`
  ADD PRIMARY KEY (`id`),
  ADD KEY `empruntés-livres_ibfk_1` (`id-d'emprunteur`),
  ADD KEY `empruntés-livres_ibfk_2` (`id-livre`);

--
-- Index pour la table `livre`
--
ALTER TABLE `livre`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `bibliothécaire`
--
ALTER TABLE `bibliothécaire`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT pour la table `empruntés-livres`
--
ALTER TABLE `empruntés-livres`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT pour la table `livre`
--
ALTER TABLE `livre`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `empruntés-livres`
--
ALTER TABLE `empruntés-livres`
  ADD CONSTRAINT `empruntés-livres_ibfk_1` FOREIGN KEY (`id-d'emprunteur`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `empruntés-livres_ibfk_2` FOREIGN KEY (`id-livre`) REFERENCES `livre` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
