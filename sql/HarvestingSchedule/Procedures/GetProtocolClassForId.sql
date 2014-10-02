create procedure GetProtocolClassForId
	@pid int
AS 
   select [class] from [protocol] where id = @pid