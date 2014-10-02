create procedure GetProtocolPathForId
	@pid int
AS 
   select [path] from [protocol] where id = @pid