SET SQL_SAFE_UPDATES = 0;

BEGIN;
	-- Inserciones en la tabla 'rol'
	INSERT INTO rol (id, tipoRol) VALUES 
		(1, "ADMIN"),
		(2, "PERSONA_HUMANA"),
		(3, "PERSONA_JURIDICA"),
		(4, "TECNICO");

-- Inserciones en la tabla 'usuario' (la clave es la misma para todos ellos es 'marge')
-- obviamente que 'marge' no es un secreto memorizado valido, pero hace mas rapido el inicio de sesion.
INSERT INTO usuario (id, nombre, clave, rol_id) VALUES
		(1, "admin", "$2a$10$CxQui86cEpr7PRNjTwEtVujpnac8RdmBCKRQkd0KWDjBVML6Uwmb2", 1),
		(2, "humano", "$2a$10$CxQui86cEpr7PRNjTwEtVujpnac8RdmBCKRQkd0KWDjBVML6Uwmb2", 2),
		(3, "empresa", "$2a$10$CxQui86cEpr7PRNjTwEtVujpnac8RdmBCKRQkd0KWDjBVML6Uwmb2", 3),
        (4, "tecnico", "$2a$10$CxQui86cEpr7PRNjTwEtVujpnac8RdmBCKRQkd0KWDjBVML6Uwmb2", 4);

	-- Inserción en la tabla 'persona_humana'
	INSERT INTO persona_humana (
		id, nombre, apellido, usuario_id, mail, 
		latitud, longitud, direccion, 
		nroDocumento, tipoDocumento, fechaNacimiento, puntajeActual
	) 
	VALUES (
		1, "Eitan", "Wajsberg", 2, "wajsberg.eitan@gmail.com", 
		"-34.61504806051817", "-58.42762542423143", 
		"AV HIPOLITO YRIGOYEN 4321, Comuna 5, Ciudad Autónoma de Buenos Aires", 
		45566219, "DNI", '2000-02-11', 4
	);

	-- Inserción en la tabla 'formas_contribucion_humana'
	INSERT INTO formas_contribucion_humana (personaHumana_id, contribucionesElegidas) VALUES 
		(1, "REDISTRIBUCION_VIANDAS"),
		(1, "DONACION_DINERO"), 
		(1, "DONACION_VIANDAS"),
		(1, "ENTREGA_TARJETAS");


	-- Inserción en la tabla 'tecnico'
	INSERT INTO tecnico (
		id, nombre, apellido, usuario_id, mail, 
		latitud, longitud, direccion, 
		nroDocumento, tipoDocumento, 
		distanciaMaximaEnKMParaSerAvisado, cuil
	) 
	VALUES (
		1, "Eitan", "Wajsberg", 4, "wajsberg.eitan@gmail.com", 
		"-34.61504806051817", "-58.42762542423143", 
		"AV HIPOLITO YRIGOYEN 4321, Comuna 5, Ciudad Autónoma de Buenos Aires", 
		45566219, "DNI", 
		30, 11-45566219-1
	);

	-- Inserciones en la tabla 'rubro'
	INSERT INTO rubro (id, nombre) VALUES 
		(1, "Tecnología"),
		(2, "Salud"),
		(3, "Educación"),
		(4, "Finanzas"),
		(5, "Gastronomia"),
		(6, "Electronica");

	-- Inserción en la tabla 'persona_juridica'
	INSERT INTO persona_juridica (
		id, razonSocial, tipo, usuario_id, mail, 
		latitud, longitud, direccion, rubro_id
	) 
	VALUES (
		1, "Google LLC", "Empresa", 3, "wajsberg.eitan@gmail.com", 
		"-34.60246093635789", "-58.3669353942788",
		"Alicia M. de Justo 350, Comuna 1, Ciudad Autónoma de Buenos Aires", 1
	);

	-- Inserción en la tabla 'formas_contribucion_juridica'
	INSERT INTO formas_contribucion_juridicas (personaJuridica_id, contribucionesElegidas) VALUES 
		(1, "DONACION_DINERO"),
		(1, "ENCARGARSE_DE_HELADERA"),
		(1, "OFRECER_OFERTA");

	-- Inserción en la tabla 'modelo'
	INSERT INTO modelo (
		id, modelo, 
		temperaturaMaxima, temperaturaMinima
	) 
	VALUES (
		1, "Marge Master 3000x", 
		30, 14
	);

	-- Inserción en la tabla 'heladera'
	INSERT INTO heladera (
		id, nombre, capacidadMaximaVianda, 
		latitud, longitud, 
		direccion, 
		estadoHeladera, fechaRegistro, temperaturaEsperada, 
		modelo_id, personaJuridica_id
	) 
	VALUES (
		1, "Heladera Medrano", 100, 
		"-34.59860117231761", "-58.420137253615586", 
		"Av. Medrano 951, Comuna 5, Ciudad Autónoma de Buenos Aires", 
		"ACTIVA", '2024-10-18', 19, 
		1, 1
	),
	(
		2, "Heladera Lugano", 100, 
		"-34.65966739636682", "-58.46802176661161",
		"Mozart 2300, Comuna 9, Ciudad Autónoma de Buenos Aires", 
		"ACTIVA", '2024-10-18', 19, 
		1, 1
	),
	(
		3, "Heladera Plaza Moreno", 100, 
		"-34.92141753328925", "-57.9546357150892",
		"C. 12, La Plata, Provincia de Buenos Aires", 
		"ACTIVA", '2024-10-18', 19, 
		1, 1
	),
	(
		4, "Heladera Centenario", 100, 
		"-34.60841175331998", "-58.43688432449764",
		"Av. Patricias Argentinas 900, Comuna 5, Ciudad Autónoma de Buenos Aires", 
		"ACTIVA", '2024-10-18', 19, 
		1, 1
	),
	(
		5, "Heladera Google", 100, 
		"-34.60246093635789", "-58.3669353942788",
		"Alicia M. de Justo 350, Comuna 1, Ciudad Autónoma de Buenos Aires", 
		"ACTIVA", '2024-10-18', 19, 
		1, 1
	);

	-- Inserción en la tabla 'vianda'
	INSERT INTO vianda (
		id, caloriasEnKcal, comida, 
		entregada, fechaCaducidad, fechaDonacion, 
		pesoEnGramos, personaHumana_id, id_heladera
	) 
	VALUES (
		1, 600, "Pollo con papas al horno", 
		true, '2024-12-18', '2024-10-18', 
		300, 1, 1
	),
	(
		2, 450, "Pollo con ensalada fresca", 
		true, '2024-12-20', '2024-10-18', 
		300, 1, 1
	),
	(
		3, 700, "Milanesa napolitana con puré", 
		true, '2024-12-22', '2024-10-18', 
		300, 1, 1
	),
	(
		4, 550, "Ravioles de verdura con salsa", 
		true, '2024-12-19', '2024-10-18', 
		300, 1, 1
	),
	(
		5, 500, "Tarta de espinaca y queso", 
		true, '2024-12-21', '2024-10-18', 
		300, 1, 1
	);

	-- Inserción en la tabla 'donacion_dinero'
	INSERT INTO donacion_dinero (
		id, fecha, monto, 
		unidadFrecuencia, id_personaHumana, id_personaJuridica
	) 
	VALUES (
		1, '2024-12-18', 1000, 
		"NINGUNA", 1, null
	),
	(
		2, '2024-12-18', 100000, 
		"DIARIA", null, 1
	),
	(
		3, '2024-12-18', 10000000, 
		"ANUAL", 1, null
	), 
	(
		4, '2024-12-18', 10000000, 
		"MENSUAL", null, 1
	);

	-- Inserción en la tabla 'distribucion_vianda'
	INSERT INTO distribucion_vianda (
		id, cantidadViandas, fecha,
		motivo, terminada, 
		id_personaHumana, id_heladeraDestino, id_heladeraOrigen
	) 
	VALUES (
		1, 1000, '2024-12-18',
		"Desperfecto en la heladera de origen", true, 
		1, 1, 2
	),
	(
		2, 1000, '2024-12-18',
		"Falta de viandas en la heladera destino", false, 
		1, 1, 2
	),
	(
		3, 1000, '2024-12-18',
		"Desperfecto en la heladera de origen", false, 
		1, 1, 2
	),
	(
		4, 1000, '2024-12-18',
		"Falta de viandas en la heladera destino", true, 
		1, 1, 2
	);

	-- Inserción en la tabla 'vianda_por_distribucion'
	INSERT INTO vianda_por_distribucion (id_distribucion_vianda, id_vianda) 
	VALUES (1, 1), (2, 2), (3, 3), (4, 4), (4, 5);

	-- Inserción en la tabla 'tarjeta'
	INSERT INTO tarjeta (
		id, codigo, fechaRecepcionColaborador, 
		fechaRecepcionPersonaVulnerable, id_colaborador_repartidor
	)
	VALUES 
	(
		1, "U_c8joHC6n8", '2024-10-16',
		'2024-10-18', 1
	);

	-- Inserción en la tabla 'persona_vulnerable'
	INSERT INTO persona_vulnerable (
		id, nombre, apellido, 
		latitud, longitud,
		direccion,
		nroDocumento, tipoDocumento,
		fechaDeNacimiento, fechaDeRegistro, menoresAcargo,
		personaQueLoRegistro_id, tarjeta_id
	)
	VALUES 
	(
		1, "Martin", "Martinez",
		"-34.61504806051817", "-58.42762542423143", 
		"AV HIPOLITO YRIGOYEN 4321, Comuna 5, Ciudad Autónoma de Buenos Aires", 
		26633428, "DNI",
		'1995-01-02', '2000-01-02', 1,
		1, 1
	);
    
    -- Actualizacion de la persona_vulnerable de la tarjeta
    UPDATE tarjeta SET personaVulnerable_id = 1;
COMMIT;

-- Si ocurre un error, puedes ejecutar esto para revertir
ROLLBACK;

-- TODO: Agregar funcionalidades adicionales:
--  - Sugerencias de distribución
--  - Sugerencias de heladeras
--  - Usos de tarjetas
--  - Visitas
--  - Suscripciones
--  - Solicitudes de apertura
--  - Permisos
--  - Ofertas canjeadas
--  - Ofertas
--  - Opciones y respuestas de encuestas
--  - Gestión de incidentes
--  - Cargas masivas de datos
--  - Cambios de estado de las heladeras
--  - Cambios de temperatura de las heladeras