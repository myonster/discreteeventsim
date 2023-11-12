interface QueueSystem {
    boolean isAutomated();

    Server getServer();

    ImList<Customer> getQueue();

    int getMaxQueueSize();

    double getWaitTime();

    QueueSystem addWaitTime(double time);

    QueueSystem addToQueue(Customer customer);

    QueueSystem doneServe();

    QueueSystem serve(double time);

    boolean ableToServe();

    boolean isEmpty();

}
