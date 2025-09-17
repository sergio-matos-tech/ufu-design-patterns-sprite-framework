package spriteframework.strategy;

import spriteframework.sprite.Sprite;

// --- INTERFACE DO STRATEGY PATTERN ---
// Define a interface comum para todas as estratégias de movimento.
// Qualquer classe que implemente esta interface pode ser usada pelo Sprite
// para definir seu comportamento de movimento.
public interface IMovementStrategy {
    void move(Sprite sprite);
}
