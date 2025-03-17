package co.jedal.test

import co.jedal.test.integration.rabbitmq.Consumer

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    Consumer().consumeFromExchange()
}

