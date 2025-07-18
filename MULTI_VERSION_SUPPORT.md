# Suporte Multi-Versão do MCC Island Enhanced

## Versões Suportadas

O mod **MCC Island Enhanced** agora suporta as seguintes versões do Minecraft:

- **1.19.4** até **1.21.7**
- Fabric Loader **0.14.0** ou superior

## Funcionalidades Implementadas

### 1. **Detecção Automática de Versão**
- O mod detecta automaticamente a versão do Minecraft em execução
- Logs informativos mostram a versão detectada e status de compatibilidade
- Fallback seguro para versões não reconhecidas

### 2. **Sistema de Compatibilidade**
- **MinecraftVersionUtil**: Utilitário para detecção e comparação de versões
- **MinecraftCompat**: Camada de compatibilidade para diferentes versões
- Verificação de funcionalidades por versão

### 3. **Mixins Compatíveis**
- Mixins atualizados para usar targets dinâmicos
- Injeções mais flexíveis com `remap = false`
- Tratamento de erros para prevenir crashes

### 4. **Funcionalidades por Versão**

#### Todas as Versões (1.19.4+)
- ✅ **Otimização de Partículas**: Reduz lag melhorando performance
- ✅ **Sistema de Keybindings**: Teclas personalizáveis
- ✅ **Configuração Básica**: Sistema de configuração do mod

#### Versões 1.20.0+
- ✅ **Renderização Moderna**: Melhorias no sistema de renderização
- ✅ **HUD Avançado**: Interface de usuário aprimorada
- ✅ **Efeitos Visuais**: Efeitos visuais melhorados

#### Versões 1.21.0+
- ✅ **Funcionalidades Experimentais**: Recursos mais recentes
- ✅ **Otimizações Avançadas**: Melhorias de performance

## Estrutura do Código

### Classes Principais

1. **MinecraftVersionUtil**
   - Detecção automática de versão
   - Comparação de versões
   - Verificação de compatibilidade

2. **MinecraftCompat**
   - Camada de compatibilidade
   - Mapeamento de funcionalidades por versão
   - Targets dinâmicos para mixins

3. **Mixins Atualizados**
   - `ParticleManagerMixin`: Otimização de partículas
   - `InGameHudMixin`: Modificações no HUD
   - `GameRendererMixin`: Melhorias de renderização
   - `WorldRendererMixin`: Renderização do mundo
   - `ClientPlayerEntityMixin`: Modificações no jogador

### Configuração Maven

```xml
<properties>
    <minecraft.version>1.21.1</minecraft.version>
    <fabric.loader.version>0.16.0</fabric.loader.version>
    <fabric.api.version>0.102.0+1.21.1</fabric.api.version>
</properties>
```

### Configuração Fabric

```json
{
  "depends": {
    "fabricloader": ">=0.14.0",
    "fabric-api": "*",
    "minecraft": ">=1.19.4 <=1.21.7"
  }
}
```

## Como Usar

### Instalação
1. Certifique-se de ter o Fabric Loader instalado
2. Coloque o arquivo `.jar` do mod na pasta `mods`
3. O mod detectará automaticamente sua versão do Minecraft

### Verificação de Compatibilidade
O mod registrará no log:
```
[INFO] Detected Minecraft version: 1.21.1
[INFO] Minecraft version 1.21.1 is supported!
```

### Funcionalidades Condicionais
- Funcionalidades são habilitadas automaticamente baseadas na versão
- Não há necessidade de configuração manual
- O mod funciona de forma otimizada para cada versão

## Desenvolvimento

### Adicionando Novas Funcionalidades

1. **Verificar Compatibilidade**:
```java
if (MinecraftCompat.supportsFeature(MinecraftCompat.CompatFeature.MODERN_RENDERING)) {
    // Implementar funcionalidade
}
```

2. **Detectar Versão**:
```java
MinecraftVersionUtil.MinecraftVersion version = MinecraftVersionUtil.getCurrentVersion();
if (version.isAtLeast(MinecraftVersionUtil.MinecraftVersion.MC_1_20_0)) {
    // Código para versões 1.20.0+
}
```

3. **Mixins Seguros**:
```java
@Mixin(targets = "net.minecraft.client.render.GameRenderer")
public class MyMixin {
    @Inject(method = "render*", at = @At("HEAD"), remap = false)
    private void onRender(CallbackInfo ci) {
        try {
            // Código do mixin
        } catch (Exception e) {
            // Fail silently
        }
    }
}
```

## Notas Técnicas

- **Compatibilidade Java**: Requer Java 17+
- **Mixin Compatibility**: `JAVA_17` para máxima compatibilidade
- **Error Handling**: Falhas silenciosas para prevenir crashes
- **Performance**: Otimizado para cada versão específica

## Suporte e Problemas

Se encontrar problemas com versões específicas:
1. Verifique os logs para mensagens de compatibilidade
2. Confirme que sua versão está na faixa suportada (1.19.4 - 1.21.7)
3. Reporte problemas específicos da versão no GitHub

## Futuras Atualizações

- Suporte para novas versões do Minecraft será adicionado conforme necessário
- Funcionalidades específicas de versão serão expandidas
- Otimizações contínuas para melhor performance