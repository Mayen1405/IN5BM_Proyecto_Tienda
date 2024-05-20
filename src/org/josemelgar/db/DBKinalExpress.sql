-- DDL
-- Pablo Melgar
-- carne 2020478


drop database if exists DBKinalExpress; 

create database DBKinalExpress;

set global time_zone = "-6:00";

use DBKinalExpress;

create table TipoProducto(
	codigoTipoProducto int  not null,
    descripcion varchar(45),
    primary key PK_CodigoTipoProducto(codigoTipoProducto)
);

create table Compras(
	codigoCompra int  not null,
	fechaDocumento date,
    descripcion varchar(60),
    totalDocumento decimal(10,2),
    primary key PK_CodigoCompra(codigoCompra)
);

create table CargoEmpleado(
	codigoCargoEmpleado int not null,
    nombreCargo varchar(45),
    descripcionCargo varchar(45),
    primary key PK_CodigoCargoEmpleado(codigoCargoEmpleado)
);

create table Clientes(
	codigoCliente Int not null ,
    nit varchar(50),
    nombresCliente varchar(45),
    apellidosCliente varchar(45),
    direccionCliente varchar(150),
    telefonoCliente varchar(8),
    correoCliente varchar(45),
    primary key PK_CodigoCliente(codigoCliente)
);

create table Proveedores(
	codigoProveedor int  not null ,
    nitProveedor varchar(10),
    nombresProveedor varchar(60),
    apellidosProveedor varchar(60),
    direccionProveedor varchar(150),
    razonSocial varchar(60),
    contactoPrincipal varchar(60),
    paginaWeb varchar(50),
    primary key PK_CodigoProveedor(codigoProveedor)
);

Create table TelefonoProveedor(
codigoTelefonoProveedor int not null,
numeroPincipal varchar(8),
numeroSecundario varchar(8),
observaciones varchar(45),
codigoProveedor int,
primary key PK_CodigoTelefonoProveedor (codigoTelefonoProveedor),
foreign key (codigoProveedor) references Proveedores (codigoProveedor)

);

Create table EmailProveedor(
codigoEmailProveedor int not null,
emailproveedor varchar(50),
descripcion varchar(100),
codigoProveedor int,
primary key PK_CodigoEmailProveedor (codigoEmailProveedor),
foreign key (codigoProveedor) references Proveedores (codigoProveedor)
);

Create table Productos(
codigoProducto int not null,
descripcionProducto varchar(15),
precioUnitario decimal(10,2),
precioDocena decimal(10,2),
precioMayor decimal(10,2),
imagenProducto varchar(45),
existencia int,
codigoTipoProducto int,
codigoProveedor int,
primary key PK_CodigoProducto (codigoProducto),
foreign key (codigoTipoProducto) references TipoProducto(codigoTipoProducto),
foreign key (codigoProveedor) references Proveedores(codigoProveedor)
);

create table DetalleCompra(
codigoDetalleCompra int not null,
	costoUnitario decimal(10,2),
	cantidad int,
	codigoProducto int,
	codigoCompra int,
    primary key PK_CodigoDetalleCompra (codigoDetalleCompra),
	foreign key (codigoProducto) references Productos(codigoProducto),
	foreign key (codigoCompra) references Compras(codigoCompra)
);

create table Empleados(
codigoEmpleado int not null,
	nombresEmpleado varchar(50),
	apellidosEmpleado varchar(50),
	sueldo decimal(10,2),
	direccion varchar(150),
	turno varchar(15),
	codigoCargoEmpleado int,
    primary key PK_CodigoEmpleado (codigoEmpleado),
	foreign key (codigoCargoEmpleado) references CargoEmpleado(codigoCargoEmpleado)
);

create table Factura(
	codigoDeFactura int primary key not null,
	estado varchar(50),
	totalFactura decimal(10,2),
	fechaFactura varchar(45),
	codigoCliente int,
	codigoEmpleado int,
	foreign key (codigoCliente) references Clientes(codigoCliente),
	foreign key (codigoEmpleado) references Empleados(codigoEmpleado)
);

create table DetalleFactura(
	codigoDeDetalleFactura int primary key not null,
	precioUnitario decimal(10,2),
	cantidad int,
	codigoDeFactura int,
	codigoProducto int,
	foreign key (codigoDeFactura) references Factura(codigoDeFactura),
	foreign key (codigoProducto) references Productos(codigoProducto)
);


-- procesos almacenados-Clientes 

delimiter $$
create procedure sp_agregarClientes(in _codigoCliente int, in _nit varchar(10), in _nombresCliente varchar(45), in _apellidosCliente varchar(45), in _direccionCliente varchar(150), in _telefonoCliente varchar(8), in _correoCliente varchar(45))
begin
	insert into Clientes (Clientes.codigoCliente,Clientes.nit, Clientes.nombresCliente, Clientes.apellidosCliente, Clientes.direccionCliente, Clientes.telefonoCliente, Clientes.correoCliente)
    values(_codigoCliente,_nit,_nombresCliente,_apellidosCliente,_direccionCliente,_telefonoCliente, _correoCliente);
end$$
delimiter ;
call sp_agregarClientes('100','1','luis','fajardo','8va calle 34-56','45191245','LuisFajardo@gmail.com');
call sp_agregarClientes('10','2','juan','mejia','8va calle 11-23','30321277','JuanMejia@gmail.com');

delimiter $$
create procedure sp_listarClientes()
begin
	select * from Clientes;
end$$
delimiter ;
call sp_listarClientes();

delimiter $$
create procedure sp_buscarClientes(in _codigoCliente Int)
begin
	select * from Clientes where Clientes.codigoCliente = _codigoCliente ;
end$$
delimiter ;

delimiter $$
create procedure sp_actualizarCliente(in _codigoCliente Int,in _nit varchar(50), in _nombresCliente varchar(45), in _apellidosCliente varchar(45), in _direccionCliente varchar(150), in _telefonoCliente varchar(8), in _correoCliente varchar(45))
begin
	update Clientes
set
	Clientes.nit = _nit,
	Clientes.nombresiente = _nombresCliente,
	Clientes.apellidosCliente = _apellidosCliente,
	Clientes.direccionCliente = _direccionCliente,
    Clientes.telefonoCliente = _telefonoCliente,
    Clientes.correoCliente = _correoCliente
where
        Cliente.codigoCliente = _codigoCliente;
end$$
delimiter ;

delimiter $$
create procedure sp_eliminarClientes(in _codigoCliente int)
begin
    delete from Clientes where Cliente.codigoCliente = _codigoCliente;
end$$
delimiter ;

-- compras 

delimiter $$
create procedure sp_agregarCompra(in _codigoCompra int,in _fechaDocumento date,in _descripcion varchar(60), in _totalDocumento decimal(10,2))
begin
	insert into Compras (Compras.CodigoCompra,Compras.fechaDocumento, Compras.descripcion, Compras.totalDocumento)
    values(_codigoCompra,_fechaDocumento,_descripcion,_totalDocumento);
end$$
delimiter ;
call sp_agregarCompra('15','2020-05-14','fragil',40.20);
call sp_agregarCompra('16','2020-06-15','fragil',60.20);

delimiter $$
create procedure sp_listarCompras()
begin
	select * from Compras;
end$$
delimiter ;
call sp_listarCompras();

delimiter $$
create procedure sp_buscarCompras(in _codigoCompra int)
begin
	select * from Compras where Compras.codigoCompra = codigoCompra;
end$$
delimiter ;

call sp_buscarCompras(15);

delimiter $$
create procedure sp_actualizarCompra(in _codigoCompra int,in _numeroDocumento int, in _fechaDocumento date,in _descripcion varchar(60), in _totalDocumento decimal(10,2))
begin
	update Compras
set
	Compras.fechaDocumento = _fechaDocumento,
	Compras.descripcion = _descripcion,
	Compras.totalDocumento = _nombresCliente,
	Compras.totalDocumento = _totalDocumento
where
        Compras.codigoCompra = _codigoCompra;
end$$



delimiter $$
create procedure sp_eliminarCompras(in _codigoCompra int)
begin
    delete from Compras where Compras.codigoCompra = _codigoCompra;
end$$
delimiter ;

call sp_eliminarCompras(15);

-- proveedores 

delimiter $$
create procedure sp_agregarProveedores(in _codigoProveedor int, _nitProveedor varchar(10),in _nombresProveedor varchar(60),in _apellidosProveedor varchar(60),in _direccionProveedor varchar(150),in _razonSocial varchar(60),in _contactoPrincipal varchar(60),in _paginaWeb varchar(50))
begin
	insert into Proveedores (Proveedores.codigoProveedor,Proveedores.nitProveedor, Proveedores.nombresProveedor, Proveedores.apellidosProveedor, Proveedores.direccionProveedor, Proveedores.razonSocial,Proveedores.contactoPrincipal, Proveedores.paginaWeb)
    values(_codigoProveedor,_nitProveedor,_nombresProveedor,_apellidosProveedor, _direccionProveedor, _razonSocial, _contactoPrincipal, _paginaWeb);
end$$
delimiter ;
call sp_agregarProveedores('32','4519','miguel','ordoñez', '9na calle a','soporte','45191245','refrigeradores.com');
call sp_agregarProveedores('65','1245','juan','perez', '8va calle 13-21','soporte','30321277','proveedor.com');

delimiter $$
create procedure sp_listarProveedores()
begin
	select * from Proveedores;
end$$
delimiter ;
call sp_listarProveedores();

delimiter $$
create procedure sp_buscarProveedores(in _codigoProveedor int)
begin
	select * from Proveedores where Proveedores.codigoProveedor = _codigoProveedor ;
end$$
delimiter ;

call sp_buscarProveedores(32);

delimiter $$
create procedure sp_actualizarProveedores(in _codigoProveedor int ,in _nitProveedor varchar(10),in _nombresProveedor varchar(60),in _apellidosProveedor varchar(60),in _direccionProveedor varchar(150),in _razonSocial varchar(60),in _contactoPrincipal varchar(60),in _paginaWeb varchar(50))
begin
	update Proveedores
set
	Proveedores.nitProveedor = _nitProveedor,
	Proveedores.nombresProveedor = nombresProveedor,
	Proveedores.apellidosProveedor = _apellidosProveedor,
	Proveedores.direccionProveedor = _direccionProveedor,
    Proveedores.razonSocial = _razonSocial,
    Proveedores.contactoPrincipal = _contactoPrincipal,
    Proveeores.paginaWeb = _paginaWeb
where
        Proveedores.codigoProveedor = _codigoProveedor;
end$$

delimiter $$
create procedure sp_eliminarProveedores(in _codigoProveedor int)
begin
    delete from Proveedores where Proveedores.codigoProveedor = _codigoProveedor;
end$$
delimiter ;

-- call sp_eliminarProveedores(2);

-- tipoProducto
delimiter $$
create procedure sp_agregarTipoProducto( in _codigoTipoProducto int, in _descripcion varchar(45))
begin
	insert into TipoProducto (TipoProducto.codigoTipoProducto,TipoProducto.descripcion)
    values(_codigoTipoProducto,_descripcion);
end$$
delimiter ;
call sp_agregarTipoProducto('1','fragil');
call sp_agregarTipoProducto('2','fragil');

delimiter $$
create procedure sp_listarTipoProducto()
begin
	select * from TipoProducto;
end$$
delimiter ;
call sp_listarTipoProducto();

delimiter $$
create procedure sp_buscarTipoProducto(in _codigoTipoProducto int)
begin
	select * from TipoProducto where TipoProducto.codigoTipoProducto = _codigoTipoProducto ;
end$$
delimiter ;

call sp_buscarTipoProducto(1);

delimiter $$
create procedure sp_actualizarTipoProducto(in codigoTipoProducto int,in _descripcion varchar(45))
begin
	update TipoProducto
set
	TipoProducto.descripcion = _descripcion
where
        TipoProducto.codigoTipoProducto = _codigoTipoProducto;
end$$

delimiter $$
create procedure sp_eliminarTipoProducto(in _codigoTipoProducto int)
begin
    delete from TipoProducto where TipoProducto.codigoTipoProducto = _codigoTipoProducto;
end$$
delimiter ;

-- call sp_eliminarTipoProducto(2);

-- cargo Empleado
delimiter $$
create procedure sp_agregarCargoEmpleado(in _codigoCargoEmpleado int, in _nombreCargo varchar(45), in _descripcionCargo varchar(45))
begin
	insert into CargoEmpleado (CargoEmpleado.codigoCargoEmpleado,CargoEmpleado.nombreCargo, CargoEmpleado.descripcionCargo)
    values(_codigoCargoEmpleado,_nombreCargo, _descripcionCargo);
end$$
delimiter ;
call sp_agregarCargoEmpleado('8','ceo','jefe');
call sp_agregarCargoEmpleado('9','ceo','jefe');

delimiter $$
create procedure sp_listarCargoEmpleado()
begin
	select * from CargoEmpleado;
end$$
delimiter ;
call sp_listarCargoEmpleado();

delimiter $$
create procedure sp_buscarCargoEmpleado(in _codigoCargoEmpleado  int)
begin
	select * from CargoEmpleado where CargoEmpleado.codigoCargoEmpleado = _codigoCargoEmpleado;
end$$
delimiter ;

-- call sp_buscarCargoEmpleado(1);

delimiter $$
create procedure sp_actualizarCargoEmpleado(in _codigoCargoEmpleado  int,in _nombreCargo varchar(45), in _descripcionCargo varchar(45))
begin
	update CargoEmpleado
set
	CargoEmpleado.nombreCargo = _nombreCargo,
    CargoEmpleado.descripcionCargo = _descripcionCargo
where
        CargoEmpleado.codigoCargoEmpleado = _codigoCargoEmpleado;
end$$

delimiter $$
create procedure sp_eliminarCargoEmpleado(in _codigoCargoEmpleado int)
begin
    delete from CargoEmpleado where CargoEmpleado.codigoCargoEmpleado = _codigoCargoEmpleado;
end$$
delimiter ;

-- call sp_eliminarCargoEmpleado(2);

delimiter $$
create procedure sp_agregartelefono(in idtelefonoproveedor int, in numeropincipal varchar(8), in numerosecundario varchar(8), 
    in observaciones varchar(45), in idproveedores int)
begin
    insert into telefonoproveedor (idtelefonoproveedor, numeropincipal, numerosecundario, observaciones, idproveedores)
    values (idtelefonoproveedor, numeropincipal, numerosecundario, observaciones, idproveedores);
end $$
delimiter ;

-- listar telefono
delimiter $$
create procedure sp_listartelefonoproveedor ()
begin
    select idtelefonoproveedor, numeropincipal, numerosecundario, observaciones, idproveedores
    from telefonoproveedor;
end$$
delimiter ;

-- actualizar telefono 
delimiter $$
create procedure sp_actualizartelefono( in nuevoidtelefonoproveedor int, in nuevonumeropincipal varchar(8), 
in nuevonumerosecundario varchar(8), in nuevoobservaciones varchar(45), in nuevoidproveedores int)
begin
    update telefonoproveedor
    set numeropincipal = nuevonumeropincipal, 
        numerosecundario = nuevonumerosecundario, 
        observaciones = nuevoobservaciones,
        idproveedores = nuevoidproveedores
    where idtelefonoproveedor = nuevoidtelefonoproveedor;
end $$
delimiter ;

-- eliminar telefono
delimiter $$
create procedure sp_eliminartelefono(in idtelefonoproveedor int)
begin
    delete from telefonoproveedor where idtelefonoproveedor = idtelefonoproveedor;
end $$
delimiter ;

--email

delimiter $$
create procedure sp_agregaremail( in idemailproveedor int, in emailproveedor varchar(50), 
in descripcion varchar(100), in idproveedores int)
begin
    insert into emailproveedor (idemailproveedor, emailproveedor, descripcion, idproveedores)
    values (idemailproveedor, emailproveedor, descripcion, idproveedores);
end $$
delimiter ;


delimiter $$
create procedure sp_listaremail()
begin
    select idemailproveedor, emailproveedor, descripcion, idproveedores
    from emailproveedor;
end $$
delimiter ;

delimiter &&
create procedure sp_actualizaremail(in nuevoidemailproveedor int, in nuevoemailproveedor varchar(50), 
in nuevodescripcion varchar(100), in nuevoidproveedores int)
begin
    update emailproveedor
    set emailproveedor = nuevoemailproveedor, 
        descripcion = nuevodescripcion,
        idproveedores = nuevoidproveedores
    where idemailproveedor = nuevoidemailproveedor;
end &&
delimiter ;

-- eliminar email
delimiter &&
create procedure sp_eliminaremail(in idemailproveedor int)
begin
    delete from emailproveedor where idemailproveedor = idemailproveedor;
end &&
delimiter ;

----------------------------------------- productos
-- agregar producto
delimiter $$
create procedure sp_agregarproducto(in idproducto int, in descripcionproducto varchar(40), in preciounitario decimal(10,2), 
in preciodocena decimal(10,2), in preciomayor decimal(10,2), in existencia int, in idtipoproducto int, in idproveedores int)
begin
    insert into productos (idproducto, descripcionproducto, preciounitario, preciodocena, preciomayor, existencia, idtipoproducto, idproveedores)
    values (idproducto, descripcionproducto, preciounitario, preciodocena, preciomayor, existencia, idtipoproducto, idproveedores);
end $$
delimiter ;

-- listar producto
delimiter $$
create procedure sp_listarproducto()
begin
    select idproducto, descripcionproducto, preciounitario, preciodocena, preciomayor, existencia, idtipoproducto, idproveedores
    from productos;
end $$
delimiter ;

-- actualizar producto
delimiter &&
create procedure sp_actualizarproducto(in nuevoidproducto int, in nuevodescripcionproducto varchar(40), in nuevopreciounitario decimal(10,2),
in nuevopreciodocena decimal(10,2), in nuevopreciomayor decimal(10,2), in nuevoexistencia int, in nuevoidtipoproducto int, in nuevoidproveedores int)
begin
    update productos
    set descripcionproducto = nuevodescripcionproducto, 
        preciounitario = nuevopreciounitario, 
        preciodocena = nuevopreciodocena, 
        preciomayor = nuevopreciomayor, 
        existencia = nuevoexistencia,
        idtipoproducto = nuevoidtipoproducto,
        idproveedores = nuevoidproveedores
    where idproducto = nuevoidproducto;
end &&
delimiter ;

-- eliminar producto 
delimiter &&
create procedure sp_eliminarproducto(in idproducto int)
begin
    delete from productos where idproducto = idproducto;
end &&
delimiter ;

-------------- empleado

-- agregar empleado
delimiter $$
create procedure sp_agregarempleado(in idempleado int, in nombresempleado varchar(50), in apellidosempleado varchar(50),
in sueldo decimal(10,2), in direccion varchar(150), in turno varchar(15), in idcargoempleado int)
begin
    insert into Empleados (idempleado, nombresempleado, apellidosempleado, sueldo, direccion, turno, idcargoempleado)
    values (idempleado, nombresempleado, apellidosempleado, sueldo, direccion, turno, idcargoempleado);
end $$
delimiter ;

-- listar empleado 
delimiter &&
create procedure sp_listarempleado()
begin
    select codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, codigoCargoEmpleado
    from empleados;
end &&
delimiter ;

-- update
delimiter $$
create procedure sp_actualizarempleado(in _codigoEmpleado int, in _nombresEmpleado varchar(50),in _apellidosEmpleado varchar(50), in _sueldo decimal(10,2), in _direccion varchar(150), in _turno varchar(15), in _codigoCargoEmpleado int)
begin
    update Empleados
    set nombresEmpleado = _nombresEmpleado, 
        apellidosEmpleado = _apellidosEmpleado, 
        sueldo = _sueldo, 
        direccion = _direccion,
        turno = _turno,
        codigoCargoempleado = _codigoCargoEmpleado
    where codigoEmpleado = codigoEmpleado;
end $$
delimiter ;

-- delete
delimiter $$
create procedure sp_eliminarempleado(in codigoEmpleado int)
begin
    delete from Empleados where codigoEmpleado = codigoEmpleado;
end $$
delimiter ;

--factura
delimiter $$
create procedure sp_agregarfactura(in codigoDefactura int, in estado varchar(50),
in totalfactura decimal(10,2), in fechafactura varchar(45), in codigoCliente int, in codigoEmpleado int)
begin
    insert into factura (codigoDefactura, estado, totalfactura, fechafactura, codigoCliente, codigoEmpleado)
    values (codigoDefactura, estado, totalfactura, fechafactura, codigoCliente, codigoEmpleado);
end $$
delimiter ;

delimiter $$
create procedure sp_listarfactura()
begin
    select codigoDefactura, estado, totalfactura, fechafactura, codigoCliente, codigoEmpleado
    from factura;
end $$
delimiter ;

delimiter $$
create procedure sp_actualizarFactura( in _CodigoFactura int, in _estado varchar(50), in nuevototalfactura decimal(10,2),
in _fechaFactura varchar(45), in _codigoCliente int, in _codigoEmpleado int)
begin
    update Factura
    set estado = nuevoestado, 
        totalFactura = nuevototalfactura,
        fechaFactura = nuevofechafactura,
        codigoCliente = nuevoidcliente,
        codigoEmpleado = nuevoidempleado
    where factura.codigoDefactura = codigoDefactura;
end $$
delimiter ;

delimiter $$
create procedure sp_eliminarFactura(in codigoDefactura int)
begin
    delete from Factura where codigoDeFactura = codigoDeFactura;
end $$
delimiter ;
























