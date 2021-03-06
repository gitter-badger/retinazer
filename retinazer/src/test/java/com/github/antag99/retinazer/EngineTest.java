package com.github.antag99.retinazer;

import static com.github.antag99.retinazer.TestUtils.assertEqualsUnordered;
import static org.junit.Assert.assertSame;

import java.util.Arrays;

import org.junit.Test;

import com.github.antag99.retinazer.utils.Inject;

public class EngineTest {
    @Test
    public void testEngine() {
        Engine engine = EngineConfig.create().finish();
        engine.update();
        engine.update();
        engine.update();
        engine.update();
        engine.reset();
        engine.update();
        engine.reset();
        engine.reset();
        engine.update();
        engine.update();
        engine.update();
    }

    @Test
    public void testEntityRetrieval() {
        Engine engine = EngineConfig.create()
            .withComponentType(FlagComponentA.class)
            .withComponentType(FlagComponentB.class)
            .withComponentType(FlagComponentC.class)
            .finish();

        Entity entity0 = engine.createEntity();

        Entity entity1 = engine.createEntity();
        entity1.add(new FlagComponentA());

        Entity entity2 = engine.createEntity();
        entity2.add(new FlagComponentB());

        Entity entity3 = engine.createEntity();
        entity3.add(new FlagComponentC());

        Entity entity4 = engine.createEntity();
        entity4.add(new FlagComponentA());
        entity4.add(new FlagComponentB());

        Entity entity5 = engine.createEntity();
        entity5.add(new FlagComponentB());
        entity5.add(new FlagComponentC());

        Entity entity6 = engine.createEntity();
        entity6.add(new FlagComponentA());
        entity6.add(new FlagComponentC());

        Entity entity7 = engine.createEntity();
        entity7.add(new FlagComponentA());
        entity7.add(new FlagComponentB());
        entity7.add(new FlagComponentC());

        engine.update();

        assertEqualsUnordered(
            Arrays.asList(entity0, entity1, entity2, entity3, entity4, entity5, entity6, entity7),
            engine.getEntities());

        assertEqualsUnordered(
            Arrays.asList(entity0, entity1, entity2, entity3, entity4, entity5, entity6, entity7),
            engine.getEntitiesFor(Family.with()));

        assertEqualsUnordered(
            Arrays.asList(entity0, entity1, entity2, entity3, entity4, entity5, entity6, entity7),
            engine.getEntitiesFor(Family.exclude()));

        assertEqualsUnordered(
            Arrays.asList(entity0, entity1, entity2, entity3, entity4, entity5, entity6, entity7),
            engine.getEntitiesFor(Family.with().exclude()));

        assertEqualsUnordered(
            Arrays.asList(entity1, entity4, entity6, entity7),
            engine.getEntitiesFor(Family.with(FlagComponentA.class)));

        assertEqualsUnordered(
            engine.getEntitiesFor(Family.with(FlagComponentB.class)),
            Arrays.asList(entity2, entity4, entity5, entity7));

        assertEqualsUnordered(
            Arrays.asList(entity3, entity5, entity6, entity7),
            engine.getEntitiesFor(Family.with(FlagComponentC.class)));

        assertEqualsUnordered(
            Arrays.asList(entity4, entity7),
            engine.getEntitiesFor(Family.with(FlagComponentA.class, FlagComponentB.class)));

        assertEqualsUnordered(
            Arrays.asList(entity6, entity7),
            engine.getEntitiesFor(Family.with(FlagComponentA.class, FlagComponentC.class)));

        assertEqualsUnordered(
            Arrays.asList(entity5, entity7),
            engine.getEntitiesFor(Family.with(FlagComponentB.class, FlagComponentC.class)));

        assertEqualsUnordered(
            Arrays.asList(entity7),
            engine.getEntitiesFor(Family.with(FlagComponentA.class, FlagComponentB.class, FlagComponentC.class)));

        assertEqualsUnordered(
            Arrays.asList(entity0, entity2, entity3, entity5),
            engine.getEntitiesFor(Family.exclude(FlagComponentA.class)));

        assertEqualsUnordered(
            Arrays.asList(entity0, entity1, entity3, entity6),
            engine.getEntitiesFor(Family.exclude(FlagComponentB.class)));

        assertEqualsUnordered(
            Arrays.asList(entity0, entity1, entity2, entity4),
            engine.getEntitiesFor(Family.exclude(FlagComponentC.class)));

        assertEqualsUnordered(
            Arrays.asList(entity0, entity3),
            engine.getEntitiesFor(Family.exclude(FlagComponentA.class, FlagComponentB.class)));

        assertEqualsUnordered(
            Arrays.asList(entity0, entity2),
            engine.getEntitiesFor(Family.exclude(FlagComponentA.class, FlagComponentC.class)));

        assertEqualsUnordered(
            Arrays.asList(entity0, entity1),
            engine.getEntitiesFor(Family.exclude(FlagComponentB.class, FlagComponentC.class)));

        assertEqualsUnordered(
            Arrays.asList(entity0),
            engine.getEntitiesFor(
                Family.exclude(FlagComponentA.class, FlagComponentB.class, FlagComponentC.class)));

        assertEqualsUnordered(
            Arrays.asList(entity1, entity6),
            engine.getEntitiesFor(Family.with(FlagComponentA.class).exclude(FlagComponentB.class)));

        assertEqualsUnordered(
            Arrays.asList(entity1, entity4),
            engine.getEntitiesFor(Family.with(FlagComponentA.class).exclude(FlagComponentC.class)));

        assertEqualsUnordered(
            Arrays.asList(entity2, entity5),
            engine.getEntitiesFor(Family.with(FlagComponentB.class).exclude(FlagComponentA.class)));

        assertEqualsUnordered(
            Arrays.asList(entity2, entity4),
            engine.getEntitiesFor(Family.with(FlagComponentB.class).exclude(FlagComponentC.class)));

        assertEqualsUnordered(
            Arrays.asList(entity3, entity5),
            engine.getEntitiesFor(Family.with(FlagComponentC.class).exclude(FlagComponentA.class)));

        assertEqualsUnordered(
            Arrays.asList(entity3, entity6),
            engine.getEntitiesFor(Family.with(FlagComponentC.class).exclude(FlagComponentB.class)));

        assertEqualsUnordered(
            Arrays.asList(entity1),
            engine.getEntitiesFor(
                Family.with(FlagComponentA.class).exclude(FlagComponentB.class, FlagComponentC.class)));

        assertEqualsUnordered(
            Arrays.asList(entity2),
            engine.getEntitiesFor(
                Family.with(FlagComponentB.class).exclude(FlagComponentA.class, FlagComponentC.class)));

        assertEqualsUnordered(
            Arrays.asList(entity3),
            engine.getEntitiesFor(
                Family.with(FlagComponentC.class).exclude(FlagComponentA.class, FlagComponentB.class)));
    }

    private static class SimpleService {
    }

    private static class SimpleServiceConsumer {
        public @Inject SimpleService service;
    }

    @Test
    public void testDependencyInjection() {
        SimpleService service = new SimpleService();
        SimpleServiceConsumer consumer = new SimpleServiceConsumer();
        Engine engine = EngineConfig.create().withDependency(service).finish();
        engine.injectDependencies(consumer);
        assertSame(service, consumer.service);
        engine.uninjectDependencies(consumer);
        assertSame(null, consumer.service);
        engine.uninjectDependencies(consumer);
        assertSame(null, consumer.service);
    }

    private static class AbstractService {
    }

    private static class AbstractServiceImpl extends AbstractService {
    }

    private static class AbstractServiceConsumer {
        private @Inject AbstractService service;
    }

    @Test
    public void testAbstractDependencyInjection() {
        AbstractService service = new AbstractServiceImpl();
        AbstractServiceConsumer consumer = new AbstractServiceConsumer();
        Engine engine = EngineConfig.create().withDependency(service).finish();
        engine.injectDependencies(consumer);
        assertSame(service, consumer.service);
        engine.uninjectDependencies(consumer);
        assertSame(null, consumer.service);
        engine.uninjectDependencies(consumer);
        assertSame(null, consumer.service);
    }

    private interface InterfaceService {
    }

    private static class InterfaceServiceImpl implements InterfaceService {
    }

    private static class InterfaceServiceConsumer {
        private @Inject InterfaceService service;
    }

    @Test
    public void testInterfaceDependencyInjection() {
        InterfaceService service = new InterfaceServiceImpl();
        InterfaceServiceConsumer consumer = new InterfaceServiceConsumer();
        Engine engine = EngineConfig.create().withDependency(service).finish();
        engine.injectDependencies(consumer);
        assertSame(service, consumer.service);
        engine.uninjectDependencies(consumer);
        assertSame(null, consumer.service);
        engine.uninjectDependencies(consumer);
        assertSame(null, consumer.service);
    }

    private static class SimpleServiceSubclassConsumer extends SimpleServiceConsumer {
    }

    @Test
    public void testSuperclassDependencyInjection() {
        SimpleService service = new SimpleService();
        SimpleServiceSubclassConsumer consumer = new SimpleServiceSubclassConsumer();
        Engine engine = EngineConfig.create().withDependency(service).finish();
        engine.injectDependencies(consumer);
        assertSame(service, consumer.service);
        engine.uninjectDependencies(consumer);
        assertSame(null, consumer.service);
        engine.uninjectDependencies(consumer);
        assertSame(null, consumer.service);
    }

    private static class MissingService {
    }

    private static class MissingServiceConsumer {
        private @Inject MissingService service;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingDependencyInjection() {
        MissingServiceConsumer consumer = new MissingServiceConsumer();
        EngineConfig.create().finish().injectDependencies(consumer);
    }

    private static class ExampleSystem extends EntitySystem {
        public @Inject Engine engine;
        public @Inject FlagSystemA flagSystemA;
        public @Inject FlagSystemB flagSystemB;
        public @Inject FlagSystemC flagSystemC;
        public @Inject ComponentMapper<FlagComponentA> flagMapperA;
        public @Inject ComponentMapper<FlagComponentB> flagMapperB;
        public @Inject ComponentMapper<FlagComponentC> flagMapperC;
    }

    @Test
    public void testEngineDependencyInjection() {
        ExampleSystem system = new ExampleSystem();
        FlagSystemA flagSystemA = new FlagSystemA();
        FlagSystemB flagSystemB = new FlagSystemB();
        FlagSystemC flagSystemC = new FlagSystemC();
        Engine engine = EngineConfig.create()
            .withSystem(system)
            .withComponentType(FlagComponentA.class)
            .withComponentType(FlagComponentB.class)
            .withComponentType(FlagComponentC.class)
            .withSystem(flagSystemA)
            .withSystem(flagSystemB)
            .withSystem(flagSystemC).finish();
        assertSame(engine, system.engine);
        assertSame(engine.getMapper(FlagComponentA.class), system.flagMapperA);
        assertSame(engine.getMapper(FlagComponentB.class), system.flagMapperB);
        assertSame(engine.getMapper(FlagComponentC.class), system.flagMapperC);
        assertSame(flagSystemA, system.flagSystemA);
        assertSame(flagSystemB, system.flagSystemB);
        assertSame(flagSystemC, system.flagSystemC);
        engine.reset();
        assertSame(engine, system.engine);
        assertSame(engine.getMapper(FlagComponentA.class), system.flagMapperA);
        assertSame(engine.getMapper(FlagComponentB.class), system.flagMapperB);
        assertSame(engine.getMapper(FlagComponentC.class), system.flagMapperC);
        assertSame(flagSystemA, system.flagSystemA);
        assertSame(flagSystemB, system.flagSystemB);
        assertSame(flagSystemC, system.flagSystemC);
    }

    private static class MissingSystem extends EntitySystem {
    }

    private static class MissingSystemConsumer extends EntitySystem {
        public @Inject MissingSystem system;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingEngineDependencyInjection() {
        EngineConfig.create().withSystem(new MissingSystemConsumer()).finish();
    }
}
