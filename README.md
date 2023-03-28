# Terasoluna + Spring Security OAuth2 Client

## Keycloakの設定

Client scopes > roles > Mappers > client roles を選択。

Add to ID tokenをONに設定すると、`OAuth2AuthenticationToken`にClient Roleが含まれるようになる。
この場合、OAuth2AuthorizedClientからAccessTokenを取得する処理を省略できる。
