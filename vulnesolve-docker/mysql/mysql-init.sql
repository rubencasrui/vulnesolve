create database vulnesolve;
use vulnesolve;

# Crear tablas
create table configuracion_api (
	id bigint not null auto_increment,
	cantidad_resultados integer not null,
	incremento_indice integer not null,
	modo_busqueda integer not null,
	nombre varchar(16),
	solo_criticos bit not null,
	primary key (id)
) engine=InnoDB;

create table puerto (
	numero integer not null,
    descripcion varchar(1024),
    servicio varchar(16) not null,
    primary key (numero)
) engine=InnoDB;

create table usuario (
	id bigint not null auto_increment,
    clave varchar(64),
    es_admin bit not null,
    usuario varchar(16),
    primary key (id)
) engine=InnoDB;

alter table configuracion_api drop index UK_8ce97nhjfewcrw9acurgr11p2;
alter table configuracion_api add constraint UK_8ce97nhjfewcrw9acurgr11p2 unique (nombre);
alter table usuario drop index UK_i02kr8ui5pqddyd7pkm3v4jbt;
alter table usuario add constraint UK_i02kr8ui5pqddyd7pkm3v4jbt unique (usuario);


# añadir datos

insert into usuario ( usuario, clave, es_admin) values (
    'admin',
	'$2a$10$iNXNcSbWwybZQWnLW1BkC.u1ZfM6lOqztGW7TTtLTIgCMPEwNZFdq', # 123456
    1
);

select *
from usuario;

insert into configuracion_api (nombre, cantidad_resultados, modo_busqueda, solo_criticos, incremento_indice) values
('NVD-INDIVIDUAL', 500, 3, 0, 20),
('NVD-MULTIPLE',  1000, 2, 0, 20);

select *
from configuracion_api;

insert into puerto (numero, servicio, descripcion) values 
(21, 'ftp', 'El Protocolo de Transferencia de Archivos (en inglés: File Transfer Protocol, abreviado FTP) es un protocolo de red para la transferencia de archivos entre sistemas conectados a una red TCP (Transmission Control Protocol), basado en la arquitectura cliente-servidor. FTP es uno de los protocolos más antiguos y aún en uso en la actualidad.'),
(22, 'ssh', 'El Shell Seguro (en inglés: Secure Shell o SSH) es un protocolo de administración remota que permite a los usuarios controlar y modificar sus servidores remotos a través de Internet. Fue creado como una alternativa segura a los protocolos de login remoto no cifrados (como Telnet). SSH proporciona una capa de cifrado para garantizar que todas las comunicaciones entre el cliente y el servidor sean seguras.'),
(23, 'telnet', 'Telnet es un protocolo de red que se utiliza para proporcionar una comunicación bidireccional, orientada a texto, a través de conexiones TCP. Fue uno de los primeros métodos para gestionar dispositivos remotos a través de una red y es conocido por no ser seguro debido a la transmisión de datos en texto plano.'),
(25, 'smtp', 'El Protocolo Simple de Transferencia de Correo (en inglés: Simple Mail Transfer Protocol, abreviado SMTP) es un protocolo de red utilizado para el envío de correos electrónicos a través de redes IP. SMTP utiliza normalmente el puerto 25 y es el protocolo más utilizado para la transferencia de correo electrónico entre servidores.'),
(53, 'dns', 'El Sistema de Nombres de Dominio (en inglés: Domain Name System, abreviado DNS) es un sistema de nomenclatura jerárquico y descentralizado para dispositivos conectados a redes IP, como Internet. DNS asocia información variada con nombres de dominio asignados a cada una de las entidades participantes. La función más importante de DNS es traducir nombres de dominio en direcciones IP, que son necesarias para localizar y abordar estos dispositivos.'),
(69, 'tftp', 'El Protocolo Trivial de Transferencia de Archivos (en inglés: Trivial File Transfer Protocol, abreviado TFTP) es un protocolo de transferencia de archivos sencillo y sin conexión, que se utiliza principalmente para transferir pequeños archivos como configuraciones de routers y sistemas de arranque.'),
(80, 'http', 'El protocolo de transferencia de hipertexto (en inglés: Hypertext Transfer Protocol, abreviado HTTP) es el protocolo de comunicación que permite las transferencias de información a través de archivos (XML, HTML…) en la World Wide Web. Fue desarrollado por el World Wide Web Consortium y la Internet Engineering Task Force, colaboración que culminó en 1999 con la publicación de una serie de RFC, siendo el más importante de ellos el RFC 2616 que especifica la versión 1.1. HTTP define la sintaxis y la semántica que utilizan los elementos de software de la arquitectura web (clientes, servidores, proxies) para comunicarse.'),
(110, 'pop3', 'El Protocolo de Oficina de Correos versión 3 (en inglés: Post Office Protocol version 3, abreviado POP3) es un protocolo estándar de red utilizado por clientes de correo electrónico para recuperar mensajes de correo desde un servidor. POP3 es uno de los protocolos más antiguos y es compatible con casi todos los servidores y clientes de correo electrónico.'),
(119, 'nntp', 'El Protocolo de Transferencia de Noticias en Red (en inglés: Network News Transfer Protocol, abreviado NNTP) es un protocolo utilizado principalmente para leer y publicar artículos de noticias en servidores de noticias Usenet.'),
(123, 'ntp', 'El Protocolo de Tiempo de Red (en inglés: Network Time Protocol, abreviado NTP) es un protocolo de red para la sincronización de los relojes de los sistemas informáticos a través de redes de datos con latencia variable. NTP utiliza el puerto 123 y está diseñado para resistir los efectos de la variabilidad del retraso de red.'),
(135, 'rpc', 'Remote Procedure Call (RPC) es un protocolo que un programa puede utilizar para solicitar un servicio de un programa ubicado en otro ordenador en una red sin tener que entender los detalles de la red. RPC utiliza el puerto 135 para la comunicación inicial.'),
(137, 'netbios-ns', 'NetBIOS Name Service (NetBIOS-NS) utiliza el puerto 137 y se encarga de la resolución de nombres NetBIOS en direcciones IP en redes de área local. Es parte de la suite de protocolos NetBIOS utilizada en redes Windows.'),
(138, 'netbios-dgm', 'NetBIOS Datagram Service (NetBIOS-DGM) utiliza el puerto 138 y es responsable de la distribución de datagramas de NetBIOS en una red local.'),
(139, 'netbios-ssn', 'NetBIOS Session Service (NetBIOS-SSN) utiliza el puerto 139 y proporciona servicios de sesión para la comunicación en red, permitiendo la transferencia de archivos y otros datos entre computadoras.'),
(143, 'imap', 'El Protocolo de Acceso a Mensajes de Internet (en inglés: Internet Message Access Protocol, abreviado IMAP) es un protocolo de aplicación que permite el acceso a mensajes de correo electrónico en un servidor de Internet. A diferencia de POP3, IMAP permite al usuario manipular los mensajes de correo electrónico como si fueran almacenados localmente en el dispositivo del usuario, manteniéndose sincronizados con el servidor.'),
(161, 'snmp', 'El Protocolo Simple de Administración de Red (en inglés: Simple Network Management Protocol, abreviado SNMP) es un protocolo de red para la gestión de dispositivos en redes IP. SNMP se utiliza para recopilar información y configurar dispositivos como routers, switches, servidores, impresoras y otros dispositivos de red.'),
(194, 'irc', 'Internet Relay Chat (IRC) es un protocolo de comunicación en tiempo real basado en texto. Es utilizado principalmente para la comunicación en grupo en foros de discusión, denominados canales, pero también permite la comunicación directa entre dos personas.'),
(389, 'ldap', 'El Protocolo Ligero de Acceso a Directorios (en inglés: Lightweight Directory Access Protocol, abreviado LDAP) es un protocolo de aplicación para acceder y mantener servicios de directorios distribuidos a través de una red IP. LDAP es utilizado comúnmente para servicios de autenticación y autorización en entornos empresariales.'),
(443,'https','El Protocolo seguro de transferencia de hipertexto (en inglés: Hypertext Transfer Protocol Secure o https) es un protocolo de aplicación basado en el protocolo http, destinado a la transferencia segura de datos de hipertexto, es decir, es la versión segura de http. El sistema HTTPS utiliza un cifrado basado en la seguridad de textos SSL/TLS para crear un canal cifrado (cuyo nivel de cifrado depende del servidor remoto y del navegador utilizado por el cliente) más apropiado para el tráfico de información sensible que el protocolo HTTP. De este modo se consigue que la información sensible (usuario y claves de paso normalmente) no pueda ser usada por un atacante que haya conseguido interceptar la transferencia de datos de la conexión, ya que lo único que obtendrá será un flujo de datos cifrados que le resultará imposible de descifrar.'),
(445, 'microsoft-ds', 'El puerto 445 es utilizado por Microsoft Directory Services para proporcionar acceso a recursos compartidos y servicios de red como archivos e impresoras a través de la red. Es el puerto estándar para el servicio de Compartir Archivos e Impresoras en versiones modernas de Windows.'),
(514, 'syslog', 'Syslog es un protocolo estándar utilizado para enviar mensajes de registro o eventos de dispositivos de red a un servidor de registros. Syslog es comúnmente utilizado para la recopilación centralizada de datos de registro en sistemas de administración de red.'),
(1723, 'pptp', 'El Protocolo de Túnel Punto a Punto (en inglés: Point-to-Point Tunneling Protocol, abreviado PPTP) es un protocolo de red utilizado para implementar redes privadas virtuales (VPN). PPTP encapsula paquetes de datos en túneles y los transmite a través de redes IP, permitiendo conexiones seguras entre clientes y servidores.'),
(3306, 'mysql', 'MySQL es un sistema de gestión de bases de datos relacional, multihilo y multiusuario con más de seis millones de instalaciones. MySQL es uno de los sistemas de gestión de bases de datos más populares del mundo, conocido por su rendimiento y fiabilidad. Utiliza el puerto 3306 para establecer conexiones con los clientes.'),
(3389, 'rdp', 'El Protocolo de Escritorio Remoto (en inglés: Remote Desktop Protocol, abreviado RDP) es un protocolo propietario desarrollado por Microsoft, que proporciona una interfaz gráfica para conectar a otra computadora a través de una red. RDP permite a los usuarios acceder a sus escritorios remotos y utilizar sus aplicaciones, archivos y recursos como si estuvieran físicamente presentes en la máquina remota.'),
(5060, 'sip', 'El Protocolo de Inicio de Sesión (en inglés: Session Initiation Protocol, abreviado SIP) es un protocolo de señalización utilizado para iniciar, mantener y finalizar sesiones multimedia en tiempo real, como llamadas de voz y videoconferencias, a través de redes IP. SIP es un estándar ampliamente utilizado en la telefonía IP y en aplicaciones de comunicación unificada.'),
(8080, 'http-alt', 'El puerto 8080 es utilizado generalmente como una alternativa al puerto 80 para el protocolo HTTP. Es comúnmente utilizado por servidores web y proxies como una opción adicional para manejar tráfico HTTP, especialmente cuando el puerto 80 está en uso o es bloqueado. El puerto 8080 también es utilizado por varios programas y aplicaciones para funciones específicas y pruebas.');

select *
from puerto;
