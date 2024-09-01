0) Generare con
   java -jar xsd-gen-0.2.1-jar-with-dependencies.jar -prefix xs -output schema.xsd file.xml

1) Come header e footer del documento xml vanno messi
  <?xml version="1.0" encoding="UTF-8"?>
  <file xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:noNamespaceSchemaLocation="schema.xsd">

  </file>

2) Come header e footer dello schema xsd vanno messi
   <?xml version="1.0" encoding="UTF-8"?>
   <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema" elementFormDefault="qualified">

   </xs:schema>

3) In datatypes ci sono definizioni comode già fatte da cui prendere spunto o copiare e basta.
   Basta copiarle così come sono e metterle nel xsd da fare, per fare riferimento ad esse (simpletype vanno bene
   come attributi e complextype come tag) bisogna usarne il nome non qualificato (senza xs:)

4) Per usare simpletypes predefiniti invece, mettere sempre xs: come prefisso

   byte			A signed 8-bit integer
   decimal		A decimal value
   int			A signed 32-bit integer
   integer		An integer value
   long			A signed 64-bit integer
   negativeInteger	An integer containing only negative values (..,-2,-1)
   nonNegativeInteger	An integer containing only non-negative values (0,1,2,..)
   nonPositiveInteger	An integer containing only non-positive values (..,-2,-1,0)
   positiveInteger	An integer containing only positive values (1,2,..)
   short		A signed 16-bit integer
   unsignedLong		An unsigned 64-bit integer
   unsignedInt		An unsigned 32-bit integer
   unsignedShort	An unsigned 16-bit integer
   unsignedByte		An unsigned 8-bit integer
   boolean
   double
   float
   date			Defines a date value (YYYY-MM-DD)
   dateTime		Defines a date and time value (YYYY-MM-DDThh:mm:ss)
   duration		Defines a time interval
   gDay			Defines a part of a date - the day (DD)
   gMonth		Defines a part of a date - the month (MM)
   gMonthDay		Defines a part of a date - the month and day (MM-DD)
   gYear		Defines a part of a date - the year (YYYY)
   gYearMonth		Defines a part of a date - the year and month (YYYY-MM)
   time			Defines a time value (hh:mm:ss)
   normalizedString	A string that does not contain line feeds, carriage returns, or tabs


  Date, time and datetime support zones like this:
  Z
  +06:00
  -06:00