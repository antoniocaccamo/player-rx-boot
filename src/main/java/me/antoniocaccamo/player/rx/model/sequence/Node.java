/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.antoniocaccamo.player.rx.model.sequence;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author antonio.caccamo
 */
@Getter @Setter
class Node<T> {
    private final T t;
    private Node next = null;

    Node(T t) {
        this.t = t;
    }


    @Override
    public String toString() {
        
        return t.toString();
    }

}
