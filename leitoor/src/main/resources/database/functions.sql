/*¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯*/
/* TODAS AS FUNÇÕES DESCRITAS AQUI DEVEM TERMINAR COM barra/asterisco*asterisco*barra/ PARA SINALIZAR FINALIZAÇÃO DO SCRIPT */
/*-------------------------------------------------------------------------------------------------------------------------------*/

/**
 * Criar extension que remove acentos das palavras para comparações com like
 */
CREATE EXTENSION IF NOT EXISTS unaccent;/**/

/**
 * Criar função para retonar o nome do mes de acordo com uma constante
 */
CREATE OR REPLACE FUNCTION get_mes(double precision)
RETURNS varchar AS $$
    SELECT CASE $1
      WHEN 1  THEN 'Jan'
      WHEN 2  THEN 'Fev'
      WHEN 3  THEN 'Mar'
      WHEN 4  THEN 'Abr'
      WHEN 5  THEN 'Mai'
      WHEN 6  THEN 'Jun'
      WHEN 6  THEN 'Jul'
      WHEN 8  THEN 'Ago'
      WHEN 9  THEN 'Set'
      WHEN 10 THEN 'Out'
      WHEN 11 THEN 'Nov'
      ELSE 'Dez'
END
$$LANGUAGE SQL;/**/

/**
 * Criar função de índice para transformar arrays em elementos ordenáveis por ORDER BY
 */
CREATE OR REPLACE FUNCTION idx(anyarray, anyelement)
  RETURNS int AS
$$
  SELECT i FROM (
     SELECT generate_series(array_lower($1,1),array_upper($1,1))
  ) g(i)
  WHERE $1[i] = $2
  LIMIT 1;
$$ LANGUAGE sql IMMUTABLE;/**/