-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: database:3306
-- Tiempo de generación: 05-10-2023 a las 19:28:46
-- Versión del servidor: 8.1.0
-- Versión de PHP: 8.2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `foxforum`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reply`
--

CREATE TABLE `reply` (
  `id` bigint NOT NULL,
  `title` varchar(2048) CHARACTER SET utf16 COLLATE utf16_unicode_ci NOT NULL,
  `body` text CHARACTER SET utf16 COLLATE utf16_unicode_ci NOT NULL,
  `id_user` bigint NOT NULL DEFAULT '1',
  `id_thread` bigint NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Volcado de datos para la tabla `reply`
--

INSERT INTO `reply` (`id`, `title`, `body`, `id_user`, `id_thread`) VALUES
(1, 'Mi primer post', 'Este es mi primer post en mi nuevo forum', 1, 1),
(2, 'Prueba', 'Lorem ipsum', 1, 1),
(3, 'title0', 'body0', 1, 1),
(4, 'title1', 'body1', 1, 1),
(5, 'title2', 'body2', 1, 1),
(6, 'title3', 'body3', 1, 1),
(7, 'title4', 'body4', 1, 1),
(8, 'title5', 'body5', 1, 1),
(9, 'title6', 'body6', 1, 1),
(10, 'title7', 'body7', 1, 1),
(11, 'title8', 'body8', 1, 1),
(12, 'title9', 'body9', 1, 1),
(14, 'title11', 'body11', 1, 1),
(15, 'title12', 'body12', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `thread`
--

CREATE TABLE `thread` (
  `id` bigint NOT NULL,
  `title` varchar(2048) CHARACTER SET utf16 COLLATE utf16_unicode_ci NOT NULL,
  `id_user` bigint NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Volcado de datos para la tabla `thread`
--

INSERT INTO `thread` (`id`, `title`, `id_user`) VALUES
(1, 'Mi primer thread', 1),
(2, 'Hola', 1),
(3, 'Prueba', 1),
(4, 'title0', 1),
(5, 'title1', 1),
(6, 'title2', 1),
(7, 'title3', 1),
(8, 'title4', 1),
(9, 'title5', 1),
(10, 'title6', 1),
(11, 'title7', 1),
(12, 'title8', 1),
(14, 'title10', 1),
(15, 'title11', 1),
(16, 'title12', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `id` bigint NOT NULL,
  `name` varchar(255) CHARACTER SET utf16 COLLATE utf16_unicode_ci NOT NULL,
  `surname` varchar(255) CHARACTER SET utf16 COLLATE utf16_unicode_ci NOT NULL,
  `lastname` varchar(255) CHARACTER SET utf16 COLLATE utf16_unicode_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf16 COLLATE utf16_unicode_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf16 COLLATE utf16_unicode_ci NOT NULL,
  `password` varchar(512) CHARACTER SET utf16 COLLATE utf16_unicode_ci NOT NULL,
  `role` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`id`, `name`, `surname`, `lastname`, `email`, `username`, `password`, `role`) VALUES
(1, 'Rafa', 'Aznar', 'Aparici', 'raznar@ausiasmarch.net', 'rafaaznar', 'rafaaznar', 0),
(1383, 'name0', 'surname0', 'lastname0', 'email0', 'username0', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1384, 'name1', 'surname1', 'lastname1', 'email1', 'username1', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1385, 'name2', 'surname2', 'lastname2', 'email2', 'username2', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1386, 'name3', 'surname3', 'lastname3', 'email3', 'username3', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1387, 'name4', 'surname4', 'lastname4', 'email4', 'username4', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1388, 'name5', 'surname5', 'lastname5', 'email5', 'username5', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1389, 'name6', 'surname6', 'lastname6', 'email6', 'username6', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1390, 'name7', 'surname7', 'lastname7', 'email7', 'username7', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1391, 'name8', 'surname8', 'lastname8', 'email8', 'username8', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1392, 'name9', 'surname9', 'lastname9', 'email9', 'username9', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1393, 'name10', 'surname10', 'lastname10', 'email10', 'username10', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1394, 'name11', 'surname11', 'lastname11', 'email11', 'username11', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1395, 'name12', 'surname12', 'lastname12', 'email12', 'username12', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1396, 'name0', 'surname0', 'lastname0', 'email0', 'username0', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1397, 'name1', 'surname1', 'lastname1', 'email1', 'username1', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1398, 'name2', 'surname2', 'lastname2', 'email2', 'username2', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1399, 'name3', 'surname3', 'lastname3', 'email3', 'username3', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1400, 'name4', 'surname4', 'lastname4', 'email4', 'username4', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1401, 'name5', 'surname5', 'lastname5', 'email5', 'username5', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1402, 'name6', 'surname6', 'lastname6', 'email6', 'username6', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1403, 'name7', 'surname7', 'lastname7', 'email7', 'username7', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1404, 'name8', 'surname8', 'lastname8', 'email8', 'username8', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1405, 'name9', 'surname9', 'lastname9', 'email9', 'username9', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1407, 'name11', 'surname11', 'lastname11', 'email11', 'username11', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1408, 'name12', 'surname12', 'lastname12', 'email12', 'username12', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1409, 'name13', 'surname13', 'lastname13', 'email13', 'username13', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1410, 'name14', 'surname14', 'lastname14', 'email14', 'username14', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1411, 'name15', 'surname15', 'lastname15', 'email15', 'username15', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1412, 'name16', 'surname16', 'lastname16', 'email16', 'username16', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1413, 'name17', 'surname17', 'lastname17', 'email17', 'username17', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1414, 'name18', 'surname18', 'lastname18', 'email18', 'username18', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1),
(1415, 'name19', 'surname19', 'lastname19', 'email19', 'username19', 'e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `reply`
--
ALTER TABLE `reply`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `thread`
--
ALTER TABLE `thread`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `reply`
--
ALTER TABLE `reply`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `thread`
--
ALTER TABLE `thread`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1416;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
