interface QueueSystem {
    boolean isAutomated();

    Server getServer(int id);

    Server optimalServer();

    ImList<Customer> getQueue();

    int getMaxQueueSize();

    double getWaitTime();

    double getNextTime();

    QueueSystem addWaitTime(double time);

    QueueSystem addToQueue(Customer customer);

    QueueSystem doneServe(Customer customer);

    QueueSystem serve(double time, Customer customer);

    boolean ableToServe();

    boolean isEmpty();

}
