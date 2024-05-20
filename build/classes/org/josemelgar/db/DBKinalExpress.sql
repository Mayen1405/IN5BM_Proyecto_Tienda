-- DDL
-- Pablo Melgar
-- carne 2020478


drop database if exists DBKinalExpress; 

create database DBKinalExpress;

set global time_zone = "-6:00";

use DBKinalExpress;

create table TipoProducto(
	codigoTipoProducto int  not null unique auto_increment,
    descripcion varchar(45),
    primary key PK_codigoTipoProducto(codigoTipoProducto)
);

create table Compras(
	numeroDocumento int  not null auto_increment,
	fechaDocumento date,
    descripcion varchar(60),
    totalDocumento decimal(10,2),
    primary key PK_numeroDocumento(numeroDocumento)
);

create table CargoEmpleado(
	codigoCargoEmpleado int primary key not null unique auto_increment,
    nombreCargo varchar(45),
    descripcionCargo varchar(45)
);

create table Clientes(
	codigoCliente Int not null auto_increment,
    nit varchar(50),
    nombresCliente varchar(45),
    apellidosCliente varchar(45),
    direccionCliente varchar(150),
    telefonoCliente varchar(8),
    correoCliente varchar(45),
    primary key PK_codigoCliente(codigoCliente)
);

create table Proveedores(
	codigoProveedor int  not null auto_increment,
    nitProveedor varchar(10),
    nombresProveedor varchar(60),
    apellidosProveedor varchar(60),
    direccionProveedor varchar(150),
    razonSocial varchar(60),
    contactoPrincipal varchar(60),
    paginaWeb varchar(50),
    primary key PK_codigoProveedor(codigoProveedor)
);

Create table TelefonoProveedor(

codigoTelefonoProveedor int,
numeroPincipal varchar(8),
numeroSecundario varchar(8),
observaciones varchar(45),
codigoProveedor int,
primary key PK_codigoTelefonoProveedor (codigoTelefonoProveedor),
foreign key (codigoProveedor) references Proveedores (codigoProveedor)

);

Create table EmailProveedor(

codigoEmailProveedor int,
emailproveedor varchar(50),
descripcion varchar(100),
codigoProveedor int,
primary key PK_codigoEmailProveedor (codigoEmailProveedor),
foreign key (codigoProveedor) references Proveedores (codigoProveedor)

);

Create table Productos(
	
codigoProducto varchar(15),
descripcionProducto varchar(15),
precioUnitario decimal(10,2),
precioDocena decimal(10,2),
precioMayor decimal(10,2),
imagenProducto varchar(45),
existencia int,
codigoTipoProducto int,
codigoProveedor int,
primary key PK_codigoProducto (codigoProducto),
foreign key (codigoTipoProducto) references TipoProducto(codigoTipoProducto),
foreign key (codigoProveedor) references Proveedores(codigoProveedor)
    
);


-- procesos almacenados-Clientes 

delimiter $$
create procedure sp_agregarClientes(in _nit varchar(10), in _nombresCliente varchar(45), in _apellidosCliente varchar(45), in _direccionCliente varchar(150), in _telefonoCliente varchar(8), in _correoCliente varchar(45))
begin
	insert into Clientes (Clientes.nit, Clientes.nombresCliente, Clientes.apellidosCliente, Clientes.direccionCliente, Clientes.telefonoCliente, Clientes.correoCliente)
    values(_nit,_nombresCliente,_apellidosCliente,_direccionCliente,_telefonoCliente, _correoCliente);
end$$
delimiter ;
call sp_agregarClientes('1','luis','fajardo','8va calle 34-56','45191245','LuisFajardo@gmail.com');
call sp_agregarClientes('2','juan','mejia','8va calle 11-23','30321277','JuanMejia@gmail.com');

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
create procedure sp_agregarCompra(in _fechaDocumento date,in _descripcion varchar(60), in _totalDocumento decimal(10,2))
begin
	insert into Compras (Compras.fechaDocumento, Compras.descripcion, Compras.totalDocumento)
    values(_fechaDocumento,_descripcion,_totalDocumento);
end$$
delimiter ;
call sp_agregarCompra('2020-05-14','fragil',40.20);
call sp_agregarCompra('2020-06-15','fragil',60.20);

delimiter $$
create procedure sp_listarCompras()
begin
	select * from Compras;
end$$
delimiter ;
call sp_listarCompras();

delimiter $$
create procedure sp_buscarCompras(in _numeroDocumento int)
begin
	select * from Compras where Compras.numeroDocumento = _numeroDocumento ;
end$$
delimiter ;

call sp_buscarCompras(1);

delimiter $$
create procedure sp_actualizarCompra(in _numeroDocumento int, in _fechaDocumento date,in _descripcion varchar(60), in _totalDocumento decimal(10,2))
begin
	update Compras
set
	Compras.fechaDocumento = _fechaDocumento,
	Compras.descripcion = _descripcion,
	Compras.totalDocumento = _nombresCliente,
	Compras.totalDocumento = _totalDocumento
where
        Compras.numeroDocumento = _numeroDocumento;
end$$



delimiter $$
create procedure sp_eliminarCompras(in _numeroDocumento int)
begin
    delete from Compras where Compras.numeroDocumento = _numeroDocumento;
end$$
delimiter ;

call sp_eliminarCompras(2);

-- proveedores 

delimiter $$
create procedure sp_agregarProveedores(in _nitProveedor varchar(10),in _nombresProveedor varchar(60),in _apellidosProveedor varchar(60),in _direccionProveedor varchar(150),in _razonSocial varchar(60),in _contactoPrincipal varchar(60),in _paginaWeb varchar(50))
begin
	insert into Proveedores (Proveedores.nitProveedor, Proveedores.nombresProveedor, Proveedores.apellidosProveedor, Proveedores.direccionProveedor, Proveedores.razonSocial,Proveedores.contactoPrincipal, Proveedores.paginaWeb)
    values(_nitProveedor,_nombresProveedor,_apellidosProveedor, _direccionProveedor, _razonSocial, _contactoPrincipal, _paginaWeb);
end$$
delimiter ;
call sp_agregarProveedores(4519,'miguel','ordoñez', '9na calle a','soporte','45191245','refrigeradores.com');
call sp_agregarProveedores(1245,'juan','perez', '8va calle 13-21','soporte','30321277','proveedor.com');

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

call sp_buscarProveedores(1);

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
create procedure sp_agregarTipoProducto( in _descripcion varchar(45))
begin
	insert into TipoProducto (TipoProducto.descripcion)
    values(_descripcion);
end$$
delimiter ;
call sp_agregarTipoProducto('fragil');
call sp_agregarTipoProducto('fragil');

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
create procedure sp_agregarCargoEmpleado( in _nombreCargo varchar(45), in _descripcionCargo varchar(45))
begin
	insert into CargoEmpleado (CargoEmpleado.nombreCargo, CargoEmpleado.descripcionCargo)
    values(_nombreCargo, _descripcionCargo);
end$$
delimiter ;
call sp_agregarCargoEmpleado('ceo','jefe');
call sp_agregarCargoEmpleado('ceo','jefe');

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

























