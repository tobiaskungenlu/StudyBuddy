/*
 * @(#)Direct-X-Buffer.java	1.50 05/11/17
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// -- This file was mechanically generated: Do not edit! -- //

package java.nio;

import sun.misc.Cleaner;
import sun.misc.Unsafe;
import sun.nio.ch.DirectBuffer;
import sun.nio.ch.FileChannelImpl;


class DirectIntBufferU

    extends IntBuffer



    implements DirectBuffer
{



    // Cached unsafe-access object
    protected static final Unsafe unsafe = Bits.unsafe();

    // Cached unaligned-access capability
    protected static final boolean unaligned = Bits.unaligned();

    // Base address, used in all indexing calculations
    // NOTE: moved up to Buffer.java for speed in JNI GetDirectBufferAddress
    //    protected long address;

    // If this buffer is a view of another buffer then we keep a reference to
    // that buffer so that its memory isn't freed before we're done with it
    protected Object viewedBuffer = null;

    public Object viewedBuffer() {
        return viewedBuffer;
    }




































    public Cleaner cleaner() { return null; }





























































    // For duplicates and slices
    //
    DirectIntBufferU(DirectBuffer db,	        // package-private
			       int mark, int pos, int lim, int cap,
			       int off)
    {

	super(mark, pos, lim, cap);
	address = db.address() + off;
	viewedBuffer = db;






    }

    public IntBuffer slice() {
	int pos = this.position();
	int lim = this.limit();
	assert (pos <= lim);
	int rem = (pos <= lim ? lim - pos : 0);
	int off = (pos << 2);
        assert (off >= 0);
	return new DirectIntBufferU(this, -1, 0, rem, rem, off);
    }

    public IntBuffer duplicate() {
	return new DirectIntBufferU(this,
					      this.markValue(),
					      this.position(),
					      this.limit(),
					      this.capacity(),
					      0);
    }

    public IntBuffer asReadOnlyBuffer() {

	return new DirectIntBufferRU(this,
					   this.markValue(),
					   this.position(),
					   this.limit(),
					   this.capacity(),
					   0);



    }



    public long address() {
	return address;
    }

    private long ix(int i) {
        return address + (i << 2);
    }

    public int get() {
	return ((unsafe.getInt(ix(nextGetIndex()))));
    }

    public int get(int i) {
	return ((unsafe.getInt(ix(checkIndex(i)))));
    }

    public IntBuffer get(int[] dst, int offset, int length) {

	if ((length << 2) > Bits.JNI_COPY_TO_ARRAY_THRESHOLD) {
	    checkBounds(offset, length, dst.length);
	    int pos = position();
	    int lim = limit();
	    assert (pos <= lim);
	    int rem = (pos <= lim ? lim - pos : 0);
	    if (length > rem)
		throw new BufferUnderflowException();

	    if (order() != ByteOrder.nativeOrder())
		Bits.copyToIntArray(ix(pos), dst,
					  offset << 2,
					  length << 2);
	    else
		Bits.copyToByteArray(ix(pos), dst,
				     offset << 2,
				     length << 2);
	    position(pos + length);
	} else {
	    super.get(dst, offset, length);
	}
	return this;



    }



    public IntBuffer put(int x) {

	unsafe.putInt(ix(nextPutIndex()), ((x)));
	return this;



    }

    public IntBuffer put(int i, int x) {

	unsafe.putInt(ix(checkIndex(i)), ((x)));
	return this;



    }

    public IntBuffer put(IntBuffer src) {

	if (src instanceof DirectIntBufferU) {
	    if (src == this)
		throw new IllegalArgumentException();
	    DirectIntBufferU sb = (DirectIntBufferU)src;

	    int spos = sb.position();
	    int slim = sb.limit();
	    assert (spos <= slim);
	    int srem = (spos <= slim ? slim - spos : 0);

	    int pos = position();
	    int lim = limit();
	    assert (pos <= lim);
	    int rem = (pos <= lim ? lim - pos : 0);

	    if (srem > rem)
		throw new BufferOverflowException();
 	    unsafe.copyMemory(sb.ix(spos), ix(pos), srem << 2);
 	    sb.position(spos + srem);
 	    position(pos + srem);
	} else if (src.hb != null) {

	    int spos = src.position();
	    int slim = src.limit();
	    assert (spos <= slim);
	    int srem = (spos <= slim ? slim - spos : 0);

	    put(src.hb, src.offset + spos, srem);
	    src.position(spos + srem);

	} else {
	    super.put(src);
	}
	return this;



    }

    public IntBuffer put(int[] src, int offset, int length) {

	if ((length << 2) > Bits.JNI_COPY_FROM_ARRAY_THRESHOLD) {
	    checkBounds(offset, length, src.length);
	    int pos = position();
	    int lim = limit();
	    assert (pos <= lim);
	    int rem = (pos <= lim ? lim - pos : 0);
	    if (length > rem)
		throw new BufferOverflowException();

	    if (order() != ByteOrder.nativeOrder()) 
		Bits.copyFromIntArray(src, offset << 2,
					    ix(pos), length << 2);
	    else
		Bits.copyFromByteArray(src, offset << 2,
				       ix(pos), length << 2);
	    position(pos + length);
	} else {
	    super.put(src, offset, length);
	}
	return this;



    }
    
    public IntBuffer compact() {

	int pos = position();
	int lim = limit();
	assert (pos <= lim);
	int rem = (pos <= lim ? lim - pos : 0);

 	unsafe.copyMemory(ix(pos), ix(0), rem << 2);
 	position(rem);
	limit(capacity());
	return this;



    }

    public boolean isDirect() {
	return true;
    }

    public boolean isReadOnly() {
	return false;
    }













































    public ByteOrder order() {





	return ((ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN)
		? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);

    }


























}
