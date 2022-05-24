# texo-movies
RESTful API to read the list of nominees and winners in the Worst Film category at the Golden Raspberry Awards.

Objetivo Geral da API
API RESTful criada com o objetivo de listar de maneira personalizada os vencedores da categoria Pior Filme do Golden Raspberry Awards.
Principais Recursos Utilizados
•	Utilização de Spring Boot e Tomcat como servidor de aplicação;
•	Maven para gerenciamento das dependências;
•	A chamada REST disponibilizada está enquadrada no Nível 2 do modelo de maturidade de Leonard Richardson;
•	As respostas são disponibilizadas no formato JSON;
•	A aplicação possui alguns DTOs (Data Transfer Object) utilizados para processamento e comunicação de resposta;
•	O biblioteca lombok foi utilizada visando aumentar a praticidade e diminuindo a quantidade de código.
•	Foi utilizado o banco de dados H2 para armazenamento das informações. Vale ressaltar que este banco de dados trabalha com base na memória do computador e com isso os dados são perdidos ao finalizar a aplicação.
•	Conforme solicitado ao iniciar a aplicação ocorre o carregamento automático de algumas informações contidas em um arquivo CSV (mais detalhes logo abaixo).
•	Visando mais precisão e dinamismo, os testes foram realizados com uma base de dados ‘limpa’ onde para cada cenário são inseridos registros específicos.
Observação Importante sobre o requisito abaixo:
Obter o produtor com maior intervalo entre dois prêmios consecutivos, e o que obteve dois prêmios mais rápido.
Para resolver este requisito esta API leva em consideração cada um dos produtores mesmo que o filme tenha sido produzido em conjunto, ou seja:
O produtor John foi indicado sozinho no ano 2000 e no ano seguinte ele é indicado novamente, porém dessa vez o filme foi produzido em conjunto com o produtor Bruce.
Dessa maneira esta API irá considerar que John recebeu duas indicações e irá processar o resultado com base nessa premissa, exatamente conforme solicitado no requisito.
Importando projeto
•	Abra/importe o projeto como um projeto maven
•	Importe todas as dependências do pom.xml
•	Execute o projeto normalmente
o	Se precisar fazer isso normalmente, basta fazê-lo através da classe: com.texoit.movies.MoviesApplication.java

Endpoints
Visando facilitar a avaliação deste teste, foi criado somente um endpoint que retorna o maior e o menor intervalo de um produtor vencedor (conforme requisito).
HTTP GET: http://localhost:8080/movies/award-intervals
Retorna o produtor com maior intervalo entre dois prêmios consecutivos, e o que obteve dois prêmios mais rápido.
{"min":[{"producer":"Joel Silver","interval":1,"previousWin":1990,"followingWin":1991}],"max":[{"producer":"Matthew Vaughn","interval":13,"previousWin":2002,"followingWin":2015}]}

Carregamento Inicial
O carregamento inicial é feito através do arquivo src/main/resources/static/movielist.csv
Caso seja necessário carregar outros dados basta substituir o conteúdo deste arquivo que eles serão automaticamente carregados quando o sistema for executado.
Vale lembrar que deve ser respeitada a estrutura previamente estabelecida conforme linha abaixo:
year;title;studios;producers;winner
Testes de integração
Os testes de integração foram realizados com objetivo de que todas as camadas da aplicação fossem validadas, dessa maneira optou-se por utilizar uma base de dados limpa (sem informações) onde as informações seriam criadas e processadas de acordo com cada cenário de teste.
Ou seja, NÃO foram utilizados mockups (dados falsos). Cada teste produz seu dado, grava no banco de dados e a partir daí e chamado o endpoint que passará por todas as camadas do sistema (controller, service, repositor e DB) e retornará o resultado.
E aí sim, finalmente com base nesse resultado é que são feitas as validações.
Todos os testes estão disponíveis na classe src.test.java.com.texoit.movies.controller.MovieControllerTest
